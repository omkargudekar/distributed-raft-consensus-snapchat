package com.distsc.chat.msg.handler;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.UserContextMap;

public class LogoutHandler implements ClientMsgHandler
{
	
	public void handle(ChannelHandlerContext ctx,Request msg)
	{
		
		
		if(UserContextMap.isExist(msg.getPayload().getClientMessage().getSenderUserName()))
		{	
			ctx.close();
			UserContextMap.removeClientContext(msg.getPayload().getClientMessage().getSenderUserName());
		}
		
		
		
	}
	
}
