package com.distsc.client.msg.handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import com.distsc.comm.protobuf.MessageProto.Request;



public class ClientMessageDecoder
{
	
	static Logger logger = LoggerFactory.getLogger(ClientMessageDecoder.class);
	public synchronized static void handle(ChannelHandlerContext ctx,Request msg)
	{
		
		logger.info("Received "+msg.getPayload().getClientMessage().getClientMessageType()
							+" From "+msg.getPayload().getClientMessage().getSenderUserName());
		
		switch (msg.getPayload().getClientMessage().getClientMessageType())
		{
		
		case LOGIN:
			logger.info("Passing Message to LoginHandler"+msg);
			new LoginHandler().handle(ctx,msg);
			break;

		case MESSAGE:
			logger.info("Passing Message to MessageHandler"+msg);
			new MessageHandler().handle(ctx,msg);
			break;

		case ACKNOWLEDGE:
			logger.info("Passing Message to AckHandler"+msg);
			new AckHandler().handle(ctx,msg);
			break;

		case ERROR:
			logger.info("Passing Message to ErrorHandler"+msg);
			new ErrorHandler().handle(ctx,msg);
			break;
		case LOGOUT:
			logger.info("Passing Message to LogoutHandler"+msg);
			new LogoutHandler().handle(ctx,msg);
			break;
		
		default:
			logger.error("Unknown Message Received");
			break;
		}
		

		
	}
	
	
	
}
