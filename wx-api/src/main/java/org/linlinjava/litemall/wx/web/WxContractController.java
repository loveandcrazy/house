package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.validator.Contract;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.service.WxContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/wx/contract")
@Validated
public class WxContractController {
    private final Log logger = LogFactory.getLog(WxContractController.class);

    @Autowired
    private WxContractService wxContractService;

    /**
     * 合同列表
     *
     * @param userId   用户ID
     * @param showType 显示类型，如果是0则是全部合同
     * @param page     分页页数
     * @param limit     分页大小
     * @param sort     排序字段
     * @param order     排序方式
     * @return 合同列表
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId,
                       @RequestParam(defaultValue = "0") Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Contract @RequestParam(defaultValue = "desc") String order) {
        return wxContractService.list(userId, showType, page, limit, sort, order);
    }

    /**
     * 合同详情
     *
     * @param userId  用户ID
     * @param orderId 合同ID
     * @return 合同详情
     */
    @GetMapping("detail")
    public Object detail(@LoginUser Integer userId, @NotNull Integer orderId) {
        return wxContractService.detail(userId, orderId);
    }

    /**
     * 提交合同
     *
     * @param userId 用户ID
     * @param body   合同信息，{ cartId：xxx, addressId: xxx, couponId: xxx, message: xxx, grouponRulesId: xxx,  grouponLinkId: xxx}
     * @return 提交合同操作结果
     */
    @PostMapping("submit")
    public Object submit(@LoginUser Integer userId, @RequestBody String body) {
        return wxContractService.submit(userId, body);
    }

    /**
     * 房东取消合同
     *
     * @param userId 用户ID
     * @param body   合同信息，{ contractId：xxx }
     * @return 取消合同操作结果
     */
    @PostMapping("cancel")
    public Object cancel(@LoginUser Integer userId, @RequestBody String body) {
        return wxContractService.cancel(userId, body);
    }


    /**
     * 租户确认
     *
     * @param userId 用户ID
     * @param body   合同信息，{ contractId：xxx, idcard2: xxx }
     * @return 合同操作结果
     */
    @PostMapping("confirm")
    public Object confirm(@LoginUser Integer userId, @RequestBody String body) {
        return wxContractService.confirm(userId, body);
    }

    /**
     * 删除合同
     *
     * @param userId 用户ID
     * @param body   合同信息，{ contractId：xxx }
     * @return 合同操作结果
     */
    @PostMapping("delete")
    public Object delete(@LoginUser Integer userId, @RequestBody String body) {
        return wxContractService.delete(userId, body);
    }

    /**
     * 评价合同商品
     *
     * @param userId 用户ID
     * @param body   合同信息，{ contractId：xxx }
     * @return 合同操作结果
     */
    @PostMapping("comment")
    public Object comment(@LoginUser Integer userId, @RequestBody String body) {
        return wxContractService.comment(userId, body);
    }

}