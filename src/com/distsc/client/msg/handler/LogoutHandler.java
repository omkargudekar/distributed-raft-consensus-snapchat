package com.distsc.client.msg.handler;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.UserChannelContextMap;

public class LogoutHandler implements ClientMsgHandlerInterface
{
	
	public void handle(ChannelHandlerContext ctx,Request msg)
	{
		System.out.println("Logout From "+msg.getPayload().getClientMessage().getSenderUserName());
		
		
		if(UserChannelContextMap.isExist(msg.getPayload().getClientMessage().getSenderUserName()))
		{	
			ctx.close();
			UserChannelContextMap.removeClientContext(msg.getPayload().getClientMessage().getSenderUserName());
		}
		
		
		
	}
	
}
