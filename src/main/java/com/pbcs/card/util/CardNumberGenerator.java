package com.pbcs.card.util;

import java.security.SecureRandom;

public final class CardNumberGenerator 
{
	private static final SecureRandom RANDOM=new SecureRandom();
	
	private static final String PREFIX="5678";
	
	private CardNumberGenerator() {}
	public static String generate()
	{
		StringBuilder cardNumber=new StringBuilder(PREFIX);
		
		for(int i=0;i<12;i++)
		{
			cardNumber.append(RANDOM.nextInt(10));
		}
		return cardNumber.toString();
	}

}
