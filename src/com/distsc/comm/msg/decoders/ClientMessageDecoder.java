package com.distsc.comm.msg.decoders;
import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.client.server.ClientContext;
import com.distsc.comm.msg.queues.outbound.OuboundClientQueue;
import com.distsc.comm.protobuf.ClientMessage.ClientMsg;



public class ClientMessageDecoder
{
	
	
	public static void handle(ChannelHandlerContext ctx,ClientMsg msg)
	{
		
		switch (msg.getMessageType())
		{
		
		case HANDSHAKE:
			ClientContext.addClientContext(msg.getSenderUserName(), ctx);
			break;

		case MESSAGE:
			OuboundClientQueue.pushMessage(msg);
			break;

		case ACKNOWLEDGE:
			OuboundClientQueue.pushMessage(msg);
			break;

		case ERROR:
			OuboundClientQueue.pushMessage(msg);
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
