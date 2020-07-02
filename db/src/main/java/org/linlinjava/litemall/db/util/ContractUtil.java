package org.linlinjava.litemall.db.util;

import org.linlinjava.litemall.db.domain.Contract;

import java.util.ArrayList;
import java.util.List;
/*
 * 合同状态：
 * 101 房主生成合同，；102，租户未确认房主取消；
 * 401 租户确认合同；
 */
public class ContractUtil {
    public static final Short STATUS_CREATE = 101;//房主生成合同
    public static final Short STATUS_CANCEL = 102;//租户未确认房主取消
    public static final Short STATUS_CONFIRM = 401;//租户确认合同


    public static String orderStatusText(Contract contract) {
        int status = contract.getContractStatus().intValue();

        if (status == 101) {
            return "合同创建成功";
        }

        if (status == 102) {
            return "合同取消";
        }

        if (status == 401) {
            return "合同确认成功";
        }

        throw new IllegalStateException("contractStatus不支持");
    }


    public static ContractHandleOption build(Contract contract) {
        int status = contract.getContractStatus().intValue();
        ContractHandleOption handleOption = new ContractHandleOption();

        if (status == 101) {
            // 合同创建成功，可以取消
            handleOption.setCancel(true);

        } else if (status == 102) {
            // 合同取消成功，则可以删除
            handleOption.setDelete(true);
        } else if (status == 401) {
            // 合同确认成功,则不可以删除，可以评论
            handleOption.setDelete(false);
            handleOption.setComment(true);
        } else {
            throw new IllegalStateException("status不支持");
        }

        return handleOption;
    }

    public static List<Short> contractStatus(Integer showType) {
        // 全部合同
        if (showType == 0) {
            return null;
        }

        List<Short> status = new ArrayList<Short>(2);

        if (showType.equals(1)) {
            // 待确认合同
            status.add((short) 101);
        } else if (showType.equals(2)) {
            // 已取消合同
            status.add((short) 102);
        } else if (showType.equals(3)) {
            // 已确认合同
            status.add((short) 401);
        } else {
            return null;
        }

        return status;
    }

    public static boolean isCreateStatus(Contract contract) {
        return ContractUtil.STATUS_CREATE == contract.getContractStatus().shortValue();
    }

    public static boolean isConfirmStatus(Contract contract) {
        return ContractUtil.STATUS_CONFIRM == contract.getContractStatus().shortValue();
    }

    public static boolean isCancelStatus(Contract contract) {
        return ContractUtil.STATUS_CANCEL == contract.getContractStatus().shortValue();
    }
}
