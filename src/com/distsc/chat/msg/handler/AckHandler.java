package com.distsc.chat.msg.handler;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.UserContextMap;

public class AckHandler implements ClientMsgHandler
{

	@Override
	public void handle(ChannelHandlerContext ctx,Request msg)
	{
		MessageValidator validator=new MessageValidator();
		
		if(validator.validateMessageSize(ctx,msg)==true)
		{
			
			UserContextMap.getClientContext(msg.getPayload().getClientMessage().getReceiverUserName()).writeAndFlush(msg);
			
			
		}		
	}

}
