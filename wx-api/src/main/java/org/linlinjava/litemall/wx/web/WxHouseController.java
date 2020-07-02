package org.linlinjava.litemall.wx.web;

import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.system.SystemConfig;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Contract;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 房源服务
 */
@RestController
@RequestMapping("/wx/house")
@Validated
public class WxHouseController {
	private final Log logger = LogFactory.getLog(WxHouseController.class);

	@Autowired
	private HouseService houseService;
	
	@Autowired
	private IssueService houseIssueService;

	@Autowired
	private HouseAttributeService houseAttributeService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@Autowired
	private CollectService collectService;

	@Autowired
	private FootprintService footprintService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SearchHistoryService searchHistoryService;

	private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

	private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

	private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(16, 16, 1000, TimeUnit.MILLISECONDS, WORK_QUEUE, HANDLER);

	/**
	 * 房源详情
	 * <p>
	 * 用户可以不登录。
	 * 如果用户登录，则记录用户足迹以及返回用户收藏信息。
	 *
	 * @param userId 用户ID
	 * @param id     房源ID
	 * @return 房源详情
	 */
	@GetMapping("detail")
	public Object detail(@LoginUser Integer userId, @NotNull Integer id) {
		// 房源信息
		House info = houseService.findById(id);

		// 房源属性
		Callable<List> goodsAttributeListCallable = () -> houseAttributeService.queryByGid(id);
		

		// 房源问题，这里是一些通用问题
		Callable<List> issueCallable = () -> houseIssueService.querySelective("", 1, 4, "", "");
		

		// 评论
		Callable<Map> commentsCallable = () -> {
			List<Comment> comments = commentService.queryGoodsByGid(id, 0, 2);
			List<Map<String, Object>> commentsVo = new ArrayList<>(comments.size());
			long commentCount = PageInfo.of(comments).getTotal();
			for (Comment comment : comments) {
				Map<String, Object> c = new HashMap<>();
				c.put("id", comment.getId());
				c.put("addTime", comment.getAddTime());
				c.put("content", comment.getContent());
				c.put("adminContent", comment.getAdminContent());
				User user = userService.findById(comment.getUserId());
				c.put("nickname", user == null ? "" : user.getNickname());
				c.put("avatar", user == null ? "" : user.getAvatar());
				c.put("picList", comment.getPicUrls());
				commentsVo.add(c);
			}
			Map<String, Object> commentList = new HashMap<>();
			commentList.put("count", commentCount);
			commentList.put("data", commentsVo);
			return commentList;
		};


		// 用户收藏
		int userHasCollect = 0;
		if (userId != null) {
			userHasCollect = collectService.count(userId, id);
		}

		// 记录用户的足迹 异步处理
		if (userId != null) {
			executorService.execute(()->{
				Footprint footprint = new Footprint();
				footprint.setUserId(userId);
				footprint.setHouseId(id);
				footprintService.add(footprint);
			});
		}
		FutureTask<List> houseAttributeListTask = new FutureTask<>(goodsAttributeListCallable);
	
		FutureTask<List> issueCallableTask = new FutureTask<>(issueCallable);
		FutureTask<Map> commentsCallableTsk = new FutureTask<>(commentsCallable);
		
        
		executorService.submit(houseAttributeListTask);
		
		executorService.submit(issueCallableTask);
		executorService.submit(commentsCallableTsk);

		Map<String, Object> data = new HashMap<>();

		try {
			data.put("info", info);
			data.put("userHasCollect", userHasCollect);
			data.put("issue", issueCallableTask.get());
			data.put("comment", commentsCallableTsk.get());
			
			
			data.put("attribute", houseAttributeListTask.get());
			//SystemConfig.isAutoCreateShareImage()
			data.put("share", SystemConfig.isAutoCreateShareImage());

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		//房源分享图片地址
		data.put("shareImage", info.getShareUrl());
		return ResponseUtil.ok(data);
	}

	/**
	 * 房源分类类目
	 *
	 * @param id 分类类目ID
	 * @return 房源分类类目
	 */
	@GetMapping("category")
	public Object category(@NotNull Integer id) {
		Category cur = categoryService.findById(id);
		Category parent = null;
		List<Category> children = null;

		if (cur.getPid() == 0) {
			parent = cur;
			children = categoryService.queryByPid(cur.getId());
			cur = children.size() > 0 ? children.get(0) : cur;
		} else {
			parent = categoryService.findById(cur.getPid());
			children = categoryService.queryByPid(cur.getPid());
		}
		Map<String, Object> data = new HashMap<>();
		data.put("currentCategory", cur);
		data.put("parentCategory", parent);
		data.put("brotherCategory", children);
		return ResponseUtil.ok(data);
	}

	/**
	 * 根据条件搜素房源
	 * <p>
	 * 1. 这里的前五个参数都是可选的，甚至都是空
	 * 2. 用户是可选登录，如果登录，则记录用户的搜索关键字
	 *
	 * @param categoryId 分类类目ID，可选
	 * @param keyword    关键字，可选
	 * @param isNew      是否新品，可选
	 * @param isHot      是否热买，可选
	 * @param province    省，可选
	 * @param city        市，可选
	 * @param county      县，可选
	 * @param address_detail   详细地址，可选
	 * @param userId     用户ID
	 * @param page       分页页数
	 * @param limit       分页大小
	 * @param sort       排序方式，支持"add_time", "retail_price"或"name"
	 * @param order      排序类型，顺序或者降序
	 * @return 根据条件搜素的房源详情
	 */
	@GetMapping("list")
	public Object list(
			Integer categoryId,
			String keyword,
			Boolean isNew,
			Boolean isHot,

			String province,
			String city,
			String county,
			String address_detail,

			@LoginUser Integer userId,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort(accepts = {"add_time", "price", "name"}) @RequestParam(defaultValue = "add_time") String sort,
			@Contract @RequestParam(defaultValue = "desc") String order) {

		//添加到搜索历史
		if (userId != null && !StringUtils.isNullOrEmpty(keyword)) {
			SearchHistory searchHistoryVo = new SearchHistory();
			searchHistoryVo.setKeyword(keyword);
			searchHistoryVo.setUserId(userId);
			searchHistoryVo.setFrom("wx");
			searchHistoryService.save(searchHistoryVo);
		}

		//查询列表数据
		List<House> houseList = houseService.querySelective(categoryId, keyword, isHot, isNew, page, limit, sort, order, province, city, county, address_detail);

		// 查询房源所属类目列表。
		List<Integer> houseCatIds = houseService.getCatIds( keyword, isHot, isNew);
		List<Category> categoryList = null;
		if (houseCatIds.size() != 0) {
			categoryList = categoryService.queryL2ByIds(houseCatIds);
		} else {
			categoryList = new ArrayList<>(0);
		}

		PageInfo<House> pagedList = PageInfo.of(houseList);

		Map<String, Object> entity = new HashMap<>();
		entity.put("list", houseList);
		entity.put("total", pagedList.getTotal());
		entity.put("page", pagedList.getPageNum());
		entity.put("limit", pagedList.getPageSize());
		entity.put("pages", pagedList.getPages());
		entity.put("filterCategoryList", categoryList);

		// 因为这里需要返回额外的filterCategoryList参数，因此不能方便使用ResponseUtil.okList
		return ResponseUtil.ok(entity);
	}

	/**
	 * 房源详情页面“大家都在看”推荐房源
	 *
	 * @param id, 房源ID
	 * @return 房源详情页面推荐房源
	 */
	@GetMapping("related")
	public Object related(@NotNull Integer id) {
		House house = houseService.findById(id);
		if (house == null) {
			return ResponseUtil.badArgumentValue();
		}

		// 目前的房源推荐算法仅仅是推荐同类目的其他房源
		int cid = house.getCategoryId();

		// 查找六个相关房源
		int related = 6;
		List<House> houseList = houseService.queryByCategory(cid, 0, related);
		return ResponseUtil.okList(houseList);
	}

	/**
	 * 在售的房源总数
	 *
	 * @return 在售的房源总数
	 */
	@GetMapping("count")
	public Object count() {
		Integer houseCount = houseService.queryOnSale();
		return ResponseUtil.ok(houseCount);
	}

}