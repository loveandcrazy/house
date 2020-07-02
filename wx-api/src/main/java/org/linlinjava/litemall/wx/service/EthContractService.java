package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.core.eth.LeaseContractManager;
import org.linlinjava.litemall.db.domain.Contract;

public class EthContractService {
    public static String ethContractCreate(Contract contract){
        String contractSn = contract.getContractSn();
        Integer userid1 = contract.getUserId1();
        Integer userid2 = contract.getUserId2();
        String name1 = contract.getName1();
        String name2 = contract.getName2();
        String idcard1 = contract.getIdcard1();
        String idcard2 =  contract.getIdcard2();
        String start_time = String.valueOf(contract.getStartTime()) ;
        String end_time = String.valueOf(contract.getEndTime()) ;
        String update_time = String.valueOf(contract.getUpdateTime());
        String price = String.valueOf(contract.getPrice()) ;
        String province = contract.getProvince();
        String city = contract.getCity();
        String county = contract.getCounty();
        String address_detail = contract.getAddressDetail();


        LeaseContractManager leaseContractManager = new LeaseContractManager();
        leaseContractManager.generateContract(contractSn,userid1, userid2, name1, name2, idcard1, idcard2,
                price, start_time, end_time, update_time, province, city, county,address_detail);

        String address = leaseContractManager.getAddress();
        System.out.println(address);
        leaseContractManager = new LeaseContractManager(address);
        System.out.println("加载成功");

        boolean b1 = leaseContractManager.checkContract(contractSn,userid1, userid2, name1, name2, idcard1, idcard2,
                price, start_time, end_time, update_time, province, city, county,address_detail);


        System.out.println(b1);


        return address;
    }
}
