package org.linlinjava.litemall.core.eth;

public class LeaseContractManager {
	private Connect cont;
	private String address;
	private LeaseContract contract;
	
	//初次生成合同时使用的构造器
	public LeaseContractManager(){
		  cont = new Connect();
	}
	
	//检验，执行合约时使用的构造器，参数为部署合约时记录的账户地址hash值
	public LeaseContractManager(String address) {
		 cont = new Connect();
		 this.address = address;
		 //加载合约
		 this.contract = LeaseContract.load(address, cont.web3j, cont.credentials, Consts.GAS_PRICE,
					Consts.GAS_LIMIT);
	 }
	
	
	//返回部署后的合约地址，在执行过generation（）后可以使用
	public String getAddress() {
		 return address;
	 }
	
	//初始化合约
	public void generateContract(String contractSn, int userid1, int userid2, String name1, String name2, String idcard1, String idcard2,
								 String price, String start_time,String end_time, String update_time, String province, String city, String county,String address_detail) {
		try {
			//部署合约，保存地址
			address = 	DeployContract.deployLeaseContract(cont.web3j, cont.credentials);
			System.out.println("合约部署成功，地址为："+address);		    

			//加载合约
			 this.contract = LeaseContract.load(address, cont.web3j, cont.credentials, Consts.GAS_PRICE,
					Consts.GAS_LIMIT);
			 
			 //初始化合约内容
			contract.generateContract(contractSn,userid1, userid2, name1, name2, idcard1, idcard2,
					price, start_time, end_time, update_time, province, city, county,address_detail).send();
		 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//检验合约是否正确，正确返回true
	public boolean checkContract(String contractSn, int userid1, int userid2, String name1, String name2, String idcard1, String idcard2,
								 String price, String start_time,String end_time, String update_time, String province, String city, String county,String address_detail) {
		try {
			boolean b = contract.proveContract(contractSn,userid1, userid2, name1, name2, idcard1, idcard2,
					price, start_time, end_time, update_time, province, city, county,address_detail).send().getValue();
			return b;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//改变合约状态
	public void changeStatus(int status) {

		try {
			contract.change_status(status).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//返回合约部分内容
	public String getStartTime() throws Exception {

		return contract.getStartTime().send().getValue();
	}
	
	public String getEndTime() throws Exception {

		return contract.getEndTime().send().getValue();
	}
	
	public int getPrice() throws NumberFormatException, Exception {

		return Integer.valueOf(contract.getPrice().send().getValue().toString());
	}
	
	public int getStatus() throws NumberFormatException, Exception {

		return Integer.valueOf(contract.getStauts().send().getValue().toString());
	}
	
	
	//执行合同,参数为两用户表中存储的区块链上的账户地址hash值，user1为付款方，user2为收款方。
	public void exeLeaseContract(String address_user_1 , String address_user_2,int price) {
		UserManager user1 = new UserManager(address_user_1);
		UserManager user2 = new UserManager(address_user_2);
		
		user1.subMoney(price);
		user2.addMoney(price);

		
	}
	
}
