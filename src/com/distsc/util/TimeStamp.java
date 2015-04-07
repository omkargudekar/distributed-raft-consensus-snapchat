package com.distsc.util;

import java.sql.Timestamp;

public class TimeStamp
{
	public static String getTimeStamp()
	{
		 java.util.Date date= new java.util.Date();
		 return new Timestamp(date.getTime()).toString();
	}
}
