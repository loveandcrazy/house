package org.linlinjava.litemall.wx.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.wx.dto.HouseAllinone;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.Category;
import org.linlinjava.litemall.db.domain.House;
import org.linlinjava.litemall.db.domain.HouseAttribute;
import org.linlinjava.litemall.db.service.CategoryService;
import org.linlinjava.litemall.db.service.HouseAttributeService;
import org.linlinjava.litemall.db.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class WxHouseService {
    private final Log logger = LogFactory.getLog(WxHouseService.class);

    @Autowired
    private HouseService houseService;

    @Autowired
    private HouseAttributeService attributeService;

    @Autowired
    private CategoryService categoryService;


    public Object list(Integer houseId, String houseSn, String name,
                       Integer page, Integer limit, String sort, String order) {
        List<House> houseList = houseService.querySelective(houseId, houseSn, name, page, limit, sort, order);
        return ResponseUtil.okList(houseList);
    }

    /*
        验证房源信息
     */
    private Object validate(HouseAllinone houseAllinone) {
        House house = houseAllinone.getHouse();
        String name = house.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        String houseSn = house.getHouseSn();
        if (StringUtils.isEmpty(houseSn)) {
            return ResponseUtil.badArgument();
        }
        // 分类可以不设置，如果设置则需要验证分类存在
        Integer categoryId = house.getCategoryId();
        if (categoryId != null && categoryId != 0) {
            if (categoryService.findById(categoryId) == null) {
                return ResponseUtil.badArgumentValue();
            }
        }

        HouseAttribute[] attributes = houseAllinone.getAttributes();
        for (HouseAttribute attribute : attributes) {
            String attr = attribute.getAttribute();
            if (StringUtils.isEmpty(attr)) {
                return ResponseUtil.badArgument();
            }
            String value = attribute.getValue();
            if (StringUtils.isEmpty(value)) {
                return ResponseUtil.badArgument();
            }
        }

        return null;
    }

    /**
     * 编辑房源
     *
     * NOTE：
     * 房源涉及到2个表，
     *
     * （1）house表可以编辑字段；
     * （2）house_attribute表支持编辑、添加和删除操作。
     *
     * NOTE2:
     * 前后端这里使用了一个小技巧：
     * 如果前端传来的update_time字段是空，则说明前端已经更新了某个记录，则这个记录会更新；
     * 否则说明这个记录没有编辑过，无需更新该记录。
     *
     */
    @Transactional
    public Object update(HouseAllinone houseAllinone) {
        Object error = validate(houseAllinone);
        if (error != null) {
            return error;
        }
        House house = houseAllinone.getHouse();
        HouseAttribute[] attributes = houseAllinone.getAttributes();

        // 房源基本信息表house
        if (houseService.updateById(house) == 0) {
            throw new RuntimeException("更新数据失败");
        }

        Integer gid = house.getId();

        // 房源参数表house_attribute
        for (HouseAttribute attribute : attributes) {
            if (attribute.getId() == null || attribute.getId().equals(0)){
                attribute.setHouseId(house.getId());
                attributeService.add(attribute);
            }
            else if(attribute.getDeleted()){
                attributeService.deleteById(attribute.getId());
            }
            else if(attribute.getUpdateTime() == null){
                attributeService.updateById(attribute);
            }
        }

        return ResponseUtil.ok();
    }


    @Transactional
    public Object delete(House house) {
        Integer id = house.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        Integer gid = house.getId();
        houseService.deleteById(gid);
        attributeService.deleteByGid(gid);
        return ResponseUtil.ok();
    }

    @Transactional
    public Object create(HouseAllinone houseAllinone) {
        Object error = validate(houseAllinone);
        if (error != null) {
            return error;
        }

        House house = houseAllinone.getHouse();
        HouseAttribute[] attributes = houseAllinone.getAttributes();

//        String name = house.getName();
//        if (houseService.checkExistByName(name)) {
//            return ResponseUtil.fail(GOOD_NAME_EXIST, "商品名已经存在");
//        }


        // 房源基本信息表house
        houseService.add(house);

//        //将生成的分享图片地址写入数据库
//        String url = qCodeService.createGoodShareImage(house.getId().toString(), house.getPicUrl(), house.getName());
//        if (!StringUtils.isEmpty(url)) {
//            house.setShareUrl(url);
//            if (houseService.updateById(house) == 0) {
//                throw new RuntimeException("更新数据失败");
//            }
//        }



        // 房源参数表house_attribute
        for (HouseAttribute attribute : attributes) {
            attribute.setHouseId(house.getId());
            attributeService.add(attribute);
        }


        return ResponseUtil.ok();
    }


    /*
     *   获取房源的详细信息
     */
    public Object detail(Integer id) {
        House house = houseService.findById(id);
        List<HouseAttribute> attributes = attributeService.queryByGid(id);

        Integer categoryId = house.getCategoryId();
        Category category = categoryService.findById(categoryId);
        Integer[] categoryIds = new Integer[]{};
        if (category != null) {
            Integer parentCategoryId = category.getPid();
            categoryIds = new Integer[]{parentCategoryId, categoryId};
        }

        Map<String, Object> data = new HashMap<>();
        data.put("house", house);

        data.put("attributes", attributes);
        data.put("categoryIds", categoryIds);

        return ResponseUtil.ok(data);
    }

}
