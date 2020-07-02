package org.linlinjava.litemall.admin.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.system.SystemConfig;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 检测合同状态
 */
@Component
public class ContractJob {
    private final Log logger = LogFactory.getLog(ContractJob.class);

    @Autowired
    private ContractService contractService;

    /**
     * 自动确认合同
     * <p>
     * 定时检查合同未确认情况，如果超时 LITEMALL_ORDER_UNCONFIRM 天则自动确认合同
     * 定时时间是每天凌晨3点。
     * <p>
     * TODO
     * 注意，因为是相隔一天检查，因此导致合同真正超时时间是 [LITEMALL_ORDER_UNCONFIRM, 1 + LITEMALL_ORDER_UNCONFIRM]
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void checkContractUnconfirm() {
        logger.info("系统开启定时任务检查合同是否已经超期自动确认收货");

        List<Contract> contractList = contractService.queryUnconfirm(SystemConfig.getContractUnconfirm());
        for (Contract contract : contractList) {

            // 设置合同已取消状态
            //contract.setOrderStatus(ContractUtil.STATUS_AUTO_CONFIRM);
            //contract.setConfirmTime(LocalDateTime.now());
//            if (contractService.updateWithOptimisticLocker(contract) == 0) {
//                logger.info("合同 ID=" + contract.getId() + " 数据已经更新，放弃自动确认收货");
//            } else {
//                logger.info("合同 ID=" + contract.getId() + " 已经超期自动确认收货");
//            }
        }

        logger.info("系统结束定时任务检查合同是否已经超期自动确认收货");
    }

    /**
     * 可评价合同商品超期
     * <p>
     * 定时检查合同商品评价情况，如果确认商品超时 LITEMALL_ORDER_COMMENT 天则取消可评价状态
     * 定时时间是每天凌晨4点。
     * <p>
     * TODO
     * 注意，因为是相隔一天检查，因此导致合同真正超时时间是 [LITEMALL_ORDER_COMMENT, 1 + LITEMALL_ORDER_COMMENT]
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void checkContractComment() {
        logger.info("系统开启任务检查合同是否已经超期未评价");

        List<Contract> contractList = contractService.queryComment(SystemConfig.getContractComment());
        for (Contract contract : contractList) {
            //contract.setComments((short) 0);
//            contractService.updateWithOptimisticLocker(contract);

        }

        logger.info("系统结束任务检查合同是否已经超期未评价");
    }
}
