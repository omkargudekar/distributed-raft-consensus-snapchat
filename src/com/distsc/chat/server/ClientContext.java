package com.distsc.chat.server;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class ClientContext
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
	
	
}
