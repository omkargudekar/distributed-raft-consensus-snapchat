package com.distsc.client.msg.handler;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.UserContextMap;

public class ErrorHandler  implements ClientMsgHandlerInterface
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
