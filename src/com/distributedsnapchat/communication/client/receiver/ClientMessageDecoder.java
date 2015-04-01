package com.distributedsnapchat.communication.client.receiver;
import com.distributedsnapchat.communication.buffers.ClientMessageBuffer;
import com.distributedsnapchat.communication.protobuf.ClientMessageProto.ClientMessage;
import com.distributedsnapchat.util.ImageWriter;



public class ClientMessageDecoder
{
	
	
	public static void handle(ClientMessage msg)
	{
		ClientMessageBuffer.pushMessage(msg);
		ImageWriter imgWriter=new ImageWriter();
		imgWriter.storeImage(msg);
		
	
	}
	
	
	
}
