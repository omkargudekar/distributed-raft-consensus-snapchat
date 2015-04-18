package com.distsc.client.msg.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.UserChannelContextMap;

public class LogoutHandler implements ClientMsgHandlerInterface
{
	
	
	static Logger logger = LoggerFactory.getLogger(LogoutHandler.class);
	public void handle(ChannelHandlerContext ctx,Request msg)
	{
		logger.info("Logout From "+msg.getPayload().getClientMessage().getSenderUserName());
		
		
		if(UserChannelContextMap.isExist(msg.getPayload().getClientMessage().getSenderUserName()))
		{	
			ctx.close();
			UserChannelContextMap.removeClientContext(msg.getPayload().getClientMessage().getSenderUserName());
		}
		else
		{
			logger.info("Invalid Logout Message :"+msg);
		}
		
	}
	
}
