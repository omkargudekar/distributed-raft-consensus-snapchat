package com.distsc.comm.msg.decoders;
import com.distsc.comm.msg.queues.inbound.ClientMessageBuffer;
import com.distsc.comm.protobuf.NodeMessageProto.Message;
import com.distsc.util.ImageWriter;



public class ClientMessageDecoder
{
	
	
	public static void handle(Message msg)
	{
		System.out.println("************** Received Client Message **************");
		ClientMessageBuffer.pushMessage(msg);
		ImageWriter imgWriter=new ImageWriter();
		imgWriter.storeImage(msg);
		
	
	}
	
	
	
}
