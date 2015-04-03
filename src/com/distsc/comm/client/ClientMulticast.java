package com.distsc.comm.client;



import com.distsc.app.GlobalConfiguration;
import com.distsc.beans.Node;
import com.distsc.beans.Packet;
import com.distsc.comm.msg.queues.outbound.OutboundQueue;
import com.distsc.comm.protobuf.NodeMessageProto.Message;

public class ClientMulticast
{



	public void send(Message msg)
	{
		Packet packet=null;
		for(Node node : GlobalConfiguration.getNodes())
		{
			
			packet=new Packet();
			packet.setNode(node);
			packet.setMsg(msg);
			OutboundQueue.pushMessage(packet);
			
		}
		
	}
	


}