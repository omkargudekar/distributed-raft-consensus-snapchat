package com.distsc.comm.outbound;



import com.distsc.app.GlobalConfiguration;
import com.distsc.beans.Node;
import com.distsc.beans.Packet;
import com.distsc.comm.msg.protobuf.NodeMessageProto.Message;
import com.distsc.comm.msg.queues.outbound.OutboundClusterMessageQueue;

public class OutboundMulticast
{



	public void send(Message msg)
	{
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