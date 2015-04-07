package com.distsc.comm.outbound;



import com.distsc.app.GlobalConfiguration;
import com.distsc.beans.Node;
import com.distsc.beans.Packet;
import com.distsc.comm.msg.queues.outbound.OutboundClusterMessageQueue;
import com.distsc.comm.protobuf.NodeMessageProto.Message;

public class OutboundMulticast
{



	public void send(Message msg)
	{
		System.out.println("Sending Message to Clients...");
		Packet packet=null;
		for(Node node : GlobalConfiguration.getNodes())
		{
			
			packet=new Packet();
			packet.setNode(node);
			packet.setMsg(msg);
			OutboundClusterMessageQueue.pushMessage(packet);
			
		}
		
	}
	


}