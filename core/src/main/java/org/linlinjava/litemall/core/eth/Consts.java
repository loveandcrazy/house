package org.linlinjava.litemall.core.eth;

import java.math.BigInteger;

public class Consts {
	// GAS价格
		public static BigInteger GAS_PRICE = BigInteger.valueOf(2000_000_000L);
		// GAS上限
		public static BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000L);
		// 交易费用
		public static BigInteger GAS_VALUE = BigInteger.valueOf(100_000L);;

		// chain id,在创世区块中定义的
		public static byte CHAINID = (byte) 666;

}
