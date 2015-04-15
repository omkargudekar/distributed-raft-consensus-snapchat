package com.distsc.network.maps;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class UserChannelContextMap
{
	private static HashMap<String,ChannelHandlerContext> contextMap=new HashMap<String,ChannelHandlerContext>();
	
	public static ChannelHandlerContext getClientContext(String clinetUsername)
	{
		
		return contextMap.get(clinetUsername); 
	}
	
	public static void addClientContext(String username,ChannelHandlerContext clientContext)
	{
		System.out.println("Channel Added for User : "+username);
		contextMap.put(username, clientContext);
		
	}
	
	public static void removeClientContext(String username)
	{
		contextMap.remove(username);
		
	}
	public static boolean isExist(String username)
	{
		if(contextMap.containsKey(username))
		{
			return true;
		}
		return false;
	}
	
}
