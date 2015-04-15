package com.distsc.node.outbound;



import com.distsc.app.config.GlobalConfiguration;
import com.distsc.beans.Node;
import com.distsc.beans.Packet;
import com.distsc.comm.msg.queues.outbound.MgmtMsgOutboundQueue;
import com.distsc.comm.protobuf.NodeMessageProto.Message;

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
			MgmtMsgOutboundQueue.pushMessage(packet);
			
		}
		
	}
	


}