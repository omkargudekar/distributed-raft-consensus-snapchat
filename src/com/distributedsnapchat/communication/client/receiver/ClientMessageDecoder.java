package com.distributedsnapchat.communication.client.receiver;
import com.distributedsnapchat.communication.buffers.ClientMessageBuffer;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto;
import com.distributedsnapchat.util.ImageWriter;



public class ClientMessageDecoder
{
	
	
	public static void handle(NodeMessageProto.ClientMessage msg)
	{
		ClientMessageBuffer.pushMessage(msg);
		ImageWriter imgWriter=new ImageWriter();
		imgWriter.storeImage(msg);
		
	
	}
	
	
	
}
