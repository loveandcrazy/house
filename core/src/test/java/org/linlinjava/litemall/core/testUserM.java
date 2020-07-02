package org.linlinjava.litemall.core;

import org.linlinjava.litemall.core.eth.UserManager;

public class testUserM {
	public static void main (String args[])  {

		UserManager manager = new UserManager();
		manager.register("111","222");
		String address = manager.getAddress();
				
		System.out.println(address);
		
		 manager = new UserManager(address);
		System.out.println("加载成功");

		boolean b1 = manager.login("222");
		System.out.println(b1);
		boolean b2 = manager.login("223");
		System.out.println(b2);

	/*
		try {
			System.out.println(manager.getMoney());
			System.out.println(manager.getLevel());


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
		
		manager.changePW("111");
		boolean b3 = manager.login("222");
		System.out.println(b3);
		boolean b4 = manager.login("111");
		System.out.println(b4);

	}
}
