package org.linlinjava.litemall.core.eth;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

public class DeployContract {

		public static String deployUser(Web3j web3j,Credentials credentials) throws Exception {
		
			User contract = User.deploy(web3j, credentials, Consts.GAS_PRICE, Consts.GAS_LIMIT).send();				
			return	contract.getContractAddress();				
		
		}
		
		public static String deployLeaseContract(Web3j web3j,Credentials credentials) throws Exception {
			LeaseContract contract = LeaseContract.deploy(web3j, credentials, Consts.GAS_PRICE, Consts.GAS_LIMIT).send();				
			return	contract.getContractAddress();		
		}

}
