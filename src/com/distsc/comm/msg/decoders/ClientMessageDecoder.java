package com.distsc.comm.msg.decoders;
import io.netty.channel.ChannelHandlerContext;

import com.distsc.chat.msg.handler.*;
import com.distsc.comm.protobuf.ClientMessage.ClientMsg;



public class ClientMessageDecoder
{
	
	
	public synchronized static void handle(ChannelHandlerContext ctx,ClientMsg msg)
	{
		
		
		switch (msg.getMessageType())
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
		

		/*
		System.out.println("************** Received Client Message **************");
		ClientMessageBuffer.pushMessage(msg);
		ImageWriter imgWriter=new ImageWriter();
		imgWriter.storeImage(msg);
*/
		
	}
	
	
	
}
