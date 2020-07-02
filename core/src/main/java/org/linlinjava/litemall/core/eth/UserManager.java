package org.linlinjava.litemall.core.eth;


public class UserManager {
	private Connect cont;
	private String address;
	private User contract;
	 
	//初次注册用户时使用的构造器
	 public UserManager() {
		  cont = new Connect();
	 }
	 
	 //对用户信息进行维护时使用的构造器，根据地址加载合约
	 public UserManager(String address) {
		 cont = new Connect();
		 this.address = address;
		 //加载合约
		 this.contract = User.load(address, cont.web3j, cont.credentials, Consts.GAS_PRICE,
					Consts.GAS_LIMIT);
	 }
	 
	 //返回部署的合约地址，在执行过注册方法后可以使用
	 public String getAddress() {
		 return address;
	 }
	 
	 //注册用户，部署一个代表用户的合约，并记录地址（初始化address字段），（合法性检查应在外部完成）。
	 public void register(String name,String pw) {
		 try {
			//部署合约，保存合约地址
		    address = 	DeployContract.deployUser(cont.web3j, cont.credentials);
			System.out.println("合约部署成功，地址为："+address);		    

			//加载合约
			 this.contract = User.load(address, cont.web3j, cont.credentials, Consts.GAS_PRICE,
					Consts.GAS_LIMIT);
			contract.register(name,pw).send();
			System.out.println("注册成功");
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	 }
	 
	 public boolean login(String pw) {		
		 
		try {
			return  contract.login(pw).send().getValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("账户地址错误");
			e.printStackTrace();
			return false;
		}
		
	 }
	 
	 public void changePW(String pw) {
		 try {
			 contract.changePw(pw).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
	 }
	 
	 
	 public  void addMoney(int money) {
			try {
				contract.addMoney(money).send();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	 }
	 
	 public  void subMoney(int money) {
			try {
				contract.subMoney(money).send();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	 }
	 
	 public int getMoney() throws Exception {
		return Integer.valueOf(contract.getMoney().send().getValue().toString());
	 }
	 
	 public void setLevel(int level) {
		 try {
			contract.setlevel(level).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	 }
	 
	 public int getLevel() throws NumberFormatException, Exception {
		 return Integer.valueOf(contract.getlevel().send().getValue().toString());
	 }

}
