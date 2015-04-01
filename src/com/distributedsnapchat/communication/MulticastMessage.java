package com.distributedsnapchat.communication;


import java.util.ArrayList;

import com.distributedsnapchat.app.GlobalConfiguration;
import com.distributedsnapchat.beans.Node;
import com.distributedsnapchat.beans.Packet;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.raft.RAFTStatus;

public class MulticastMessage
{



	public void send(Message msg)
	{
		Packet packet=null;
		UnicastMessage unicastMsg=new UnicastMessage();
		for(Node node : GlobalConfiguration.getNodes())
		{
			
			packet=new Packet();
			packet.setNode(node);
			packet.setMsg(msg);
			unicastMsg.pushPacket(packet);
			
		}
		new Thread(unicastMsg).start();
	}
	


}