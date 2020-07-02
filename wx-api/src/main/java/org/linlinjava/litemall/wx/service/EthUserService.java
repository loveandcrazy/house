package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.core.eth.UserManager;

/*
 * 以太坊用户操作
 */
public class EthUserService {
    public static String ethUserCreate(String name, String password){
        UserManager userManager = new UserManager();
        userManager.register(name,password);
        String address = userManager.getAddress();

//        userManager = new UserManager(address);
//
//        boolean b1 = userManager.login("222");
//        System.out.println(b1);
//        boolean b2 = userManager.login("223");
//        System.out.println(b2);
//
//        userManager.changePW("111");
//        boolean b3 = userManager.login("222");
//        System.out.println(b3);
//        boolean b4 = userManager.login("111");
//        System.out.println(b4);

        return address;
    }

    public static boolean ethLogin(String address, String password){
        UserManager userManager = new UserManager(address);
        boolean b = userManager.login(password);

        return b;
    }
}
