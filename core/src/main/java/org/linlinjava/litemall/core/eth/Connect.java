package org.linlinjava.litemall.core.eth;

import java.io.IOException;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;


public class Connect{
		public static String PASSWORD = "123456"; //账户密码；
		// 账户文件路径
		public static String PATH = "./UTC--2020-06-29T12-37-55.320597355Z--0ea9e4dcd1459ed4357bff0e99494fa9718cfee9";
		static String url = "http://121.199.74.144:8546/";
		
		static Web3j web3j;
		static Admin admin;
		static Credentials credentials;
	
	    public Connect(){
	    	//建立连接，建立节点
	    	  web3j =  web3j = Web3j.build(new HttpService(url));
	          admin = Admin.build(new HttpService(url));
			try {
			  credentials = WalletUtils.loadCredentials(PASSWORD, PATH);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CipherException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			System.out.println("连接成功！");
			System.out.println("创建凭证成功！");


	    }
	    
	}


