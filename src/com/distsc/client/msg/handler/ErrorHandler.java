package com.distsc.client.msg.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.UserChannelContextMap;

public class ErrorHandler  implements ClientMsgHandlerInterface
{
	static Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

	@Override
	public void handle(ChannelHandlerContext ctx,Request msg)
	{
		logger.info("Passing Message to ErrorHandler"+msg);
		
		
		logger.info("Sending "+msg.getPayload().getClientMessage().getClientMessageType()
							+" From "+msg.getPayload().getClientMessage().getSenderUserName()
							+" To "+msg.getPayload().getClientMessage().getReceiverUserName());
		
		MessageValidator validator=new MessageValidator();
		
		if(validator.validateMessageSize(ctx,msg)==true)
		{
			logger.info("Valid Error Message : "+msg);

			UserChannelContextMap.getClientContext(msg.getPayload().getClientMessage().getReceiverUserName()).writeAndFlush(msg);
			
			
		}		
		else
		{
			logger.info("Invalid Error Message : "+msg);

		}

		
	}
	

}
