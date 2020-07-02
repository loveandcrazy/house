package org.linlinjava.litemall.admin.service;

import com.github.binarywang.wxpay.service.WxPayService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.*;

@Service

public class AdminContractService {
    private final Log logger = LogFactory.getLog(AdminContractService.class);

    @Autowired
    private ContractService contractService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private LogHelper logHelper;


    public Object list(Integer userId, String contractSn, LocalDateTime start, LocalDateTime end, List<Short> orderStatusArray,
                       Integer page, Integer limit, String sort, String order) {
        List<Contract> contractList = contractService.querySelective(userId, contractSn, start, end, orderStatusArray, page, limit,
                sort, order);
        return ResponseUtil.okList(contractList);
    }


    public Object detail(Integer id) {
        Contract contract = contractService.findById(id);

        //UserVo user = userService.findUserVoById(contract.getHouseId());
        Map<String, Object> data = new HashMap<>();
        data.put("contract", contract);

        //data.put("user", user);

        return ResponseUtil.ok(data);
    }



    /**
     * 删除合同
     * 1. 检测当前合同是否能够删除
     * 2. 删除合同
     *
     * @param body 合同信息，{ orderId：xxx }
     * @return 合同操作结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    public Object delete(String body) {
        Integer contractId = JacksonUtil.parseInteger(body, "contractId");
        Contract contract = contractService.findById(contractId);
        if (contract == null) {
            return ResponseUtil.badArgument();
        }

        // 如果合同不是关闭状态(已取消、系统取消、已退款、用户已确认、系统已确认)，则不能删除
//        Short status = contract.getContractStatus();
//        if (!status.equals(ContractUtil.STATUS_CANCEL) && !status.equals(OrderUtil.STATUS_AUTO_CANCEL) &&
//                !status.equals(OrderUtil.STATUS_CONFIRM) &&!status.equals(OrderUtil.STATUS_AUTO_CONFIRM) &&
//                !status.equals(OrderUtil.STATUS_REFUND_CONFIRM)) {
//            return ResponseUtil.fail(ORDER_DELETE_FAILED, "订单不能删除");
//        }
        // 删除订单
        contractService.deleteById(contractId);

        logHelper.logOrderSucceed("删除", "合同编号 " + contract.getContractSn());
        return ResponseUtil.ok();
    }

    /**
     * 回复合同商品
     *
     * @param body 合同信息，{ orderId：xxx }
     * @return 合同操作结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    public Object reply(String body) {
        Integer commentId = JacksonUtil.parseInteger(body, "commentId");
        if (commentId == null || commentId == 0) {
            return ResponseUtil.badArgument();
        }
        // 目前只支持回复一次
        Comment comment = commentService.findById(commentId);
        if(comment == null){
            return ResponseUtil.badArgument();
        }
        if (!StringUtils.isEmpty(comment.getAdminContent())) {
            return ResponseUtil.fail(ORDER_REPLY_EXIST, "合同已回复！");
        }
        String content = JacksonUtil.parseString(body, "content");
        if (StringUtils.isEmpty(content)) {
            return ResponseUtil.badArgument();
        }
        // 更新评价回复
        comment.setAdminContent(content);
        commentService.updateById(comment);

        return ResponseUtil.ok();
    }

}
