package org.linlinjava.litemall.wx.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.wx.service.EthContractService;

import org.linlinjava.litemall.core.task.TaskService;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ContractHandleOption;
import org.linlinjava.litemall.db.util.ContractUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.System;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.linlinjava.litemall.wx.util.WxResponseCode.*;

/**
 * 合同服务
 *
 * <p>
 * 合同状态：
 * 101 房主生成合同，；102，租户未确认房主取消；
 * 401 租户确认合同；
 * <p>
 * 用户操作：
 * 当101租户未确认合同时，此时房主可以进行的操作是取消合同
 * 当401租户确认合同以后，此时租户可以进行的操作是，评价商品，申请售后，或者再次购买
  */
@Service
public class WxContractService {
    private final Log logger = LogFactory.getLog(WxContractService.class);

    @Autowired
    private UserService userService;
    @Autowired
    private ContractService contractService;

    @Autowired
    private RegionService regionService;

    private EthContractService ethContractService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TaskService taskService;


    /**
     * 合同列表
     *
     * @param houseId   用户ID
     * @param showType 合同信息：
     *                 0，全部合同；
     *                 1，待确认；
     *                 2，已确认；
     *                 3，已取消；
     * @param page     分页页数
     * @param limit     分页大小
     * @return 合同列表
     */
    public Object list(Integer houseId, Integer showType, Integer page, Integer limit, String sort, String order) {
        if (houseId == null) {
            return ResponseUtil.unlogin();
        }
        List<Short> contractStatus = ContractUtil.contractStatus(showType);
        List<Contract> contractList = contractService.queryByContractStatus(houseId, page, limit, sort, order);

        List<Map<String, Object>> orderVoList = new ArrayList<>(contractList.size());
        for (Contract o : contractList) {
            Map<String, Object> contractVo = new HashMap<>();
            contractVo.put("id", o.getId());
            contractVo.put("contractSn", o.getContractSn());
            contractVo.put("price", o.getPrice());
            contractVo.put("contractStatusText", ContractUtil.orderStatusText(o));
            contractVo.put("handleOption", ContractUtil.build(o));

            orderVoList.add(contractVo);
        }

        return ResponseUtil.okList(orderVoList, contractList);
    }

    /**
     * 合同详情
     *
     * @param userId  用户ID
     * @param contractId 合同ID
     * @return 合同详情
     */
    public Object detail(Integer userId, Integer contractId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        // 合同信息
        Contract contract = contractService.findById(userId, contractId);
        if (null == contract) {
            return ResponseUtil.fail(CONTRACT_UNKNOWN, "合同不存在");
        }
        if (!contract.getUserId1().equals(userId) && !contract.getUserId2().equals(userId) ) {
            return ResponseUtil.fail(CONTRACT_INVALID, "不是当前用户的合同");
        }
        Map<String, Object> contractVo = new HashMap<String, Object>();
        contractVo.put("id", contract.getId());
        contractVo.put("contractSn", contract.getContractSn());
        contractVo.put("addTime", contract.getAddTime());
        contractVo.put("province", contract.getProvince());
        contractVo.put("city", contract.getProvince());
        contractVo.put("price", contract.getPrice());
        contractVo.put("contractStatusText", ContractUtil.orderStatusText(contract));
        contractVo.put("handleOption", ContractUtil.build(contract));

        Map<String, Object> result = new HashMap<>();
        result.put("contractInfo", contractVo);

        return ResponseUtil.ok(result);

    }

    /**
     * 提交合同
     * <p>
     * 1. 创建合同表项和合同房源表项
     *
     * @param userId1 用户ID
     * @param body   合同信息，{ idcard1: xxx,houseSn: xxx, start_time: xxx, end_time: xxx,  price: xxx}
     * @return 提交合同操作结果
     */
    @Transactional
    public Object submit(Integer userId1, String body) {

        if (userId1 == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }
        Integer houseSn = JacksonUtil.parseInteger(body, "houseSn");
        String start_time = JacksonUtil.parseString(body, "start_time");
        String end_time = JacksonUtil.parseString(body, "end_time");
        Integer the_price = JacksonUtil.parseInteger(body, "price");
        String id_card1 = JacksonUtil.parseString(body, "idcard1");
        String province = JacksonUtil.parseString(body, "province");
        String city = JacksonUtil.parseString(body, "city");
        String county = JacksonUtil.parseString(body, "county");
        String detail_address = JacksonUtil.parseString(body, "address_detail");


        if (id_card1 == null || houseSn == null || start_time == null || end_time == null || the_price == null) {
            return ResponseUtil.badArgument();
        }


        BigDecimal price = new BigDecimal(the_price);

        Integer contractId = null;
        Contract contract = null;
        // 合同
        contract = new Contract();
        contract.setUserId1(userId1);
        contract.setContractSn(contractService.generateContractSn(userId1));
        contract.setContractStatus(ContractUtil.STATUS_CREATE);
        HouseService houseService = new HouseService();


//        House house = houseService.findById(1);


//        String detailedAddress = house.getProvince() + house.getCity() + house.getCounty() + " " + house.getAddressDetail();
//        contract.setProvince(house.getProvince());
//        contract.setCity(house.getCity());
//        contract.setAddressDetail(house.getAddressDetail());
        contract.setProvince(province);
        contract.setCity(city);
        contract.setCounty(county);
        contract.setAddressDetail(detail_address);
        contract.setPrice(price);


        // 添加合同表项
        contractService.add(contract);
        contractId = contract.getId();


        Map<String, Object> data = new HashMap<>();
        data.put("contractId", contractId);

        return ResponseUtil.ok(data);
    }

    /**
     * 取消合同
     * <p>
     * 1. 检测当前合同是否能够取消；
     * 2. 设置合同取消状态；
     *      *
     * @param userId 用户ID
     * @param body   合同信息，{ contractId：xxx }
     * @return 取消合同操作结果
     */
    @Transactional
    public Object cancel(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer contractId = JacksonUtil.parseInteger(body, "contractId");
        if (contractId == null) {
            return ResponseUtil.badArgument();
        }

        Contract contract = contractService.findById(userId, contractId);
        if (contract == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!contract.getUserId1().equals(userId) && !contract.getUserId2().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        LocalDateTime preUpdateTime = contract.getUpdateTime();

        // 检测是否能够取消
        ContractHandleOption handleOption = ContractUtil.build(contract);


        // 设置合同已取消状态

        contract.setEndTime(LocalDateTime.now());
        if (contractService.updateWithOptimisticLocker(contract) == 0) {
            throw new RuntimeException("更新数据已失效");
        }

        return ResponseUtil.ok();
    }



    /**
     * 租房者确认合同
     * <p>
     * 1. 检测当前合同是否能够被租房者确认；
     * 2. 设置合同确认状态。
     *
     * @param userId2 用户ID
     * @param body   合同信息，{ contractId：xxx, idcard2 : xxx }
     * @return 合同操作结果
     */
    public Object confirm(Integer userId2, String body) {
        if (userId2 == null) {
            return ResponseUtil.unlogin();
        }
        Integer contractId = JacksonUtil.parseInteger(body, "contractId");
        String  idcard2 = JacksonUtil.parseString(body, "idcard2");
        if (contractId == null) {
            return ResponseUtil.badArgument();
        }

        Contract contract = contractService.findById(userId2, contractId);
        if (contract == null) {
            return ResponseUtil.badArgument();
        }
        if (!contract.getUserId1().equals(userId2) && !contract.getUserId2().equals(userId2)) {
            return ResponseUtil.badArgumentValue();
        }
        contract.setIdcard2(idcard2);

        ContractHandleOption handleOption = ContractUtil.build(contract);

        String address = ethContractService. ethContractCreate(contract);

        contract.setHash(address);

        if (contractService.updateWithOptimisticLocker(contract) == 0) {
            return ResponseUtil.updatedDateExpired();
        }

        return ResponseUtil.ok();
    }

    /**
     * 删除合同
     * <p>
     * 1. 检测当前合同是否可以删除；
     * 2. 删除合同。
     *
     * @param userId1 用户ID
     * @param body   合同信息，{ contractId：xxx }
     * @return 合同操作结果
     */
    public Object delete(Integer userId1, String body) {
        if (userId1 == null) {
            return ResponseUtil.unlogin();
        }
        Integer contractId = JacksonUtil.parseInteger(body, "orderId");
        if (contractId == null) {
            return ResponseUtil.badArgument();
        }

        Contract contract = contractService.findById(userId1, contractId);
        if (contract == null) {
            return ResponseUtil.badArgument();
        }
        if (!contract.getUserId1().equals(userId1) && !contract.getUserId2().equals(userId1)) {
            return ResponseUtil.badArgumentValue();
        }

        ContractHandleOption handleOption = ContractUtil.build(contract);
        if (!handleOption.isDelete()) {
            return ResponseUtil.fail(CONTRACT_INVALID_OPERATION, "合同不能删除");
        }

        // 合同contract_status没有字段用于标识删除
        // 而是存在专门的delete字段表示是否删除
        contractService.deleteById(contractId);

        return ResponseUtil.ok();
    }

    /**
     * 评价合同房源
     * <p>
     *
     *
     * @param userId 用户ID
     * @param body   合同信息，{ contractId：xxx }
     * @return 合同操作结果
     */

    public Object comment(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer contractId =  JacksonUtil.parseInteger(body, "contractId");
        Contract contract = contractService.findById(userId, contractId);
        if (contract == null) {
            return ResponseUtil.badArgumentValue();
        }
        Short contractStatus = contract.getContractStatus();
        if (!ContractUtil.isConfirmStatus(contract) ) {
            return ResponseUtil.fail(CONTRACT_INVALID_OPERATION, "当前不能评价");
        }
        if (!contract.getUserId2().equals(userId)) {
            return ResponseUtil.fail(CONTRACT_INVALID, "当前合同不属于该用户");
        }
        String content = JacksonUtil.parseString(body, "content");
        Integer star = JacksonUtil.parseInteger(body, "star");
        if (star == null || star < 0 || star > 5) {
            return ResponseUtil.badArgumentValue();
        }
        Boolean hasPicture = JacksonUtil.parseBoolean(body, "hasPicture");
        List<String> picUrls = JacksonUtil.parseStringList(body, "picUrls");
        if (hasPicture == null || !hasPicture) {
            picUrls = new ArrayList<>(0);
        }

        // 创建评价
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setType((byte) 0);
        comment.setValueId(contractId);
        comment.setStar(star.shortValue());
        comment.setContent(content);
        comment.setHasPicture(hasPicture);
        comment.setPicUrls(picUrls.toArray(new String[]{}));
        commentService.save(comment);

        return ResponseUtil.ok();
    }

}