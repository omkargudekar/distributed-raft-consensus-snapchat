package com.distsc.network.maps;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserChannelContextMap
{
	static Logger logger = LoggerFactory.getLogger(UserChannelContextMap.class);

	private static HashMap<String,ChannelHandlerContext> contextMap=new HashMap<String,ChannelHandlerContext>();
	
	public static ChannelHandlerContext getClientContext(String clinetUsername)
	{
		
		return contextMap.get(clinetUsername); 
	}
	
	public static void addClientContext(String username,ChannelHandlerContext clientContext)
	{
		logger.info("Channel Added for User : "+username);
		contextMap.put(username, clientContext);
		
	}
	
	public static void removeClientContext(String username)
	{
		logger.info("Channel Removed for User :"+username);
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
