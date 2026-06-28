package com.pbcs.card.util;

import java.security.SecureRandom;

public final class TransactionIdGenerator 
{
	private static final SecureRandom RANDOM=new SecureRandom();
	private static final String PREFIX="TXN";
	private TransactionIdGenerator() {}
	
	public static String generate()
	{
		 long number = 1000000000L + (Math.abs(RANDOM.nextLong()) % 9000000000L);

	     return PREFIX + number;
	}

}
