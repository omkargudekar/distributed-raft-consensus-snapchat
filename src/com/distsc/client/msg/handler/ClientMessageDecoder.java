package com.distsc.client.msg.handler;
import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto.Request;



public class ClientMessageDecoder
{
	
	
	public synchronized static void handle(ChannelHandlerContext ctx,Request msg)
	{
		
		
		switch (msg.getPayload().getClientMessage().getClientMessageType())
		{
		
		case LOGIN:
			new LoginHandler().handle(ctx,msg);
			break;

		case MESSAGE:
			new MessageHandler().handle(ctx,msg);
			break;

		case ACKNOWLEDGE:
			new AckHandler().handle(ctx,msg);
			break;

		case ERROR:
			new ErrorHandler().handle(ctx,msg);
			break;
		case LOGOUT:
			new LogoutHandler().handle(ctx,msg);
			break;
		
		default:
			System.out.println("Unknown Message Received");
			break;
		}
		

		
	}
	
	
	
}
