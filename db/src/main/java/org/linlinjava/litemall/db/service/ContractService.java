package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ContractMapper;
import org.linlinjava.litemall.db.dao.LocalContractMapper;
import org.linlinjava.litemall.db.domain.Contract;
import org.linlinjava.litemall.db.domain.ContractExample;
import org.linlinjava.litemall.db.util.ContractUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ContractService {
    @Resource
    private ContractMapper contractMapper;
    @Resource
    private LocalContractMapper localContractMapper;


    public int add(Contract contract) {
        contract.setAddTime(LocalDateTime.now());
        contract.setUpdateTime(LocalDateTime.now());
        return contractMapper.insertSelective(contract);
    }

    public int count(Integer userId) {
        ContractExample example = new ContractExample();
        example.or().andIdEqualTo(userId).andDeletedEqualTo(false);
        return (int) contractMapper.countByExample(example);
    }

    public Contract findById(Integer orderId) {
        return contractMapper.selectByPrimaryKey(orderId);
    }

    public Contract findById(Integer userId, Integer orderId) {
        ContractExample example = new ContractExample();
        example.or().andIdEqualTo(orderId).andUserId1EqualTo(userId);
        return contractMapper.selectOneByExample(example);
    }

    private String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public int countByContractSn(Integer userId, String contractSn) {
        ContractExample example = new ContractExample();
        example.or().andUserId1EqualTo(userId).andContractSnEqualTo(contractSn);
        return (int) contractMapper.countByExample(example);
    }

    // TODO 这里应该产生一个唯一的订单，但是实际上这里仍然存在两个订单相同的可能性
    public String generateContractSn(Integer userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String orderSn = now + getRandomNum(6);
        while (countByContractSn(userId, orderSn) != 0) {
            orderSn = now + getRandomNum(6);
        }
        return orderSn;
    }

    public List<Contract> queryByContractStatus(Integer userId, Integer page, Integer limit, String sort, String order) {
        ContractExample example = new ContractExample();
        example.setOrderByClause(Contract.Column.addTime.desc());
        ContractExample.Criteria criteria = example.or();
        criteria.andUserId1EqualTo(userId);

        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return contractMapper.selectByExample(example);
    }

    public List<Contract> querySelective(Integer userId, String orderSn, LocalDateTime start, LocalDateTime end, List<Short> orderStatusArray, Integer page, Integer limit, String sort, String order) {
        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();

        if (userId != null) {
            criteria.andUserId1EqualTo(userId);
        }
        if (!StringUtils.isEmpty(orderSn)) {
            criteria.andContractSnEqualTo(orderSn);
        }
        if(start != null){
            criteria.andAddTimeGreaterThanOrEqualTo(start);
        }
        if(end != null){
            criteria.andAddTimeLessThanOrEqualTo(end);
        }
        if (orderStatusArray != null && orderStatusArray.size() != 0) {
            criteria.andContractStatusIn(orderStatusArray);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return contractMapper.selectByExample(example);
    }

//    public int updateWithOptimisticLocker(Contract order) {
//        LocalDateTime preUpdateTime = order.getUpdateTime();
//        order.setUpdateTime(LocalDateTime.now());
//        return contractMapper.updateWithOptimisticLocker(preUpdateTime, order);
//    }

    public void deleteById(Integer id) {
        contractMapper.logicalDeleteByPrimaryKey(id);
    }

    public int count() {
        ContractExample example = new ContractExample();
        example.or().andDeletedEqualTo(false);
        return (int) contractMapper.countByExample(example);
    }



    public List<Contract> queryUnconfirm(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.minusDays(days);
        ContractExample example = new ContractExample();
        //example.or().andContractStatusEqualTo(ContractUtil.STATUS_SHIP).andShipTimeLessThan(expired).andDeletedEqualTo(false);
        return contractMapper.selectByExample(example);
    }

    public Contract findBySn(String orderSn) {
        ContractExample example = new ContractExample();
        example.or().andContractSnEqualTo(orderSn).andDeletedEqualTo(false);
        return contractMapper.selectOneByExample(example);
    }
    /*
    * 用户用来获取全部合同
    * 未完工
    * */
    public Map<Object, Object> contractInfo(Integer userId) {
        ContractExample example = new ContractExample();
        example.or().andUserId1EqualTo(userId);
        List<Contract> contracts = contractMapper.selectByExampleSelective(example, Contract.Column.contractStatus, Contract.Column.userId1);

        int unpaid = 0;
        int unship = 0;
        int unrecv = 0;
        int uncomment = 0;
        for (Contract contract : contracts) {
            if (ContractUtil.isCreateStatus(contract)) {
                unpaid++;
            } else if (ContractUtil.isCreateStatus(contract)) {
                unship++;
            } else {
                // do nothing
            }
        }

        Map<Object, Object> contractInfo = new HashMap<Object, Object>();
        contractInfo.put("unpaid", unpaid);
        contractInfo.put("unship", unship);
        contractInfo.put("unrecv", unrecv);
        contractInfo.put("uncomment", uncomment);
        return contractInfo;

    }

    public List<Contract> queryComment(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.minusDays(days);
        ContractExample example = new ContractExample();
        //example.or().andCommentsGreaterThan((short) 0).andConfirmTimeLessThan(expired).andDeletedEqualTo(false);
        return contractMapper.selectByExample(example);
    }

    public int updateWithOptimisticLocker(Contract contract) {
        LocalDateTime preUpdateTime = contract.getUpdateTime();
        contract.setUpdateTime(LocalDateTime.now());
        return localContractMapper.updateWithOptimisticLocker(preUpdateTime, contract);

    }


}
