package com.distsc.network;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class UserContextMap
{
	private static HashMap<String,ChannelHandlerContext> contextMap=new HashMap<String,ChannelHandlerContext>();
	
	public static ChannelHandlerContext getClientContext(String clinetUsername)
	{
		
		return contextMap.get(clinetUsername); 
	}
	
	public static void addClientContext(String username,ChannelHandlerContext clientContext)
	{
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
