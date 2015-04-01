package com.distributedsnapchat.communication.client.receiver;
import com.distributedsnapchat.communication.buffers.ClientMessageBuffer;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.util.ImageWriter;



public class ClientMessageDecoder
{
	
	
	public static void handle(Message msg)
	{
		System.out.println("************************ Received Client Message**************");
		ClientMessageBuffer.pushMessage(msg);
		ImageWriter imgWriter=new ImageWriter();
		imgWriter.storeImage(msg);
		
	
	}
	
	
	
}
