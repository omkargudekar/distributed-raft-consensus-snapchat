package com.distsc.client.msg.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.UserChannelContextMap;

public class AckHandler implements ClientMsgHandlerInterface
{
	static Logger logger = LoggerFactory.getLogger(AckHandler.class);

	@Override
	public void handle(ChannelHandlerContext ctx,Request msg)
	{
		
		logger.debug("Handling Request "+msg);
		MessageValidator validator=new MessageValidator();
		

		logger.info("Sending "+msg.getPayload().getClientMessage().getClientMessageType()
							+" From "+msg.getPayload().getClientMessage().getSenderUserName()
							+" To "+msg.getPayload().getClientMessage().getReceiverUserName());
		
		if(validator.validateMessageSize(ctx,msg)==true)
		{	
			logger.debug("Valid Ack Message :"+msg);
			UserChannelContextMap.getClientContext(msg.getPayload().getClientMessage().getReceiverUserName()).writeAndFlush(msg);
				
		}	
		else
		{
			logger.debug("Invalid Ack Message :"+msg);
		}
	}

}
