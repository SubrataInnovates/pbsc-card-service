package com.pbcs.card.util;

import java.security.SecureRandom;

public final class CvvGenerator 
{
	private static final SecureRandom RANDOM=new SecureRandom();
	
	private CvvGenerator() {}
	
	public static String generate()
	{
		return String.format("%03d",RANDOM.nextInt(1000));
	}
}
