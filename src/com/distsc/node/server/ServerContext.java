package com.distsc.node.server;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class ServerContext
{
	private static HashMap<String,ChannelHandlerContext> contextMap=new HashMap<String,ChannelHandlerContext>();
	
	public static ChannelHandlerContext getClientContext(String nodeId)
	{
		
		return contextMap.get(nodeId); 
	}
	
	public static void addServerChnnelContext(String username,ChannelHandlerContext serverContext)
	{
		contextMap.put(username, serverContext);
		
	}
	
	public static void removeServerChannelContext(String username)
	{
		contextMap.remove(username);
		
	}
	public static boolean isExist(String nodeId)
	{
		if(contextMap.containsKey(nodeId))
		{
			return true;
		}
		return false;
	}
	
}
