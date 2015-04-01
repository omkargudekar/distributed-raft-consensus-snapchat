package com.distributedsnapchat.communication;


import java.util.ArrayList;

import com.distributedsnapchat.beans.Node;
import com.distributedsnapchat.beans.Packet;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.raft.RAFTStatus;

public class MulticastMessage
{

//	public  void send(ArrayList<Node> nodes, String message)
//	{	
//		
//		for(Node node : nodes)
//		{
//			System.out.println("Sending Message to "+node);
//			new Thread(new UnicastMessage(node.getNodeIP(),node.getNodePort(),message)).start();
//		}
//		
//	}

	public void send(Message msg)
	{
		Packet packet=null;
		UnicastMessage unicastMsg=new UnicastMessage();
		for(Node node : RAFTStatus.getNodes())
		{
			
			packet=new Packet();
			packet.setNode(node);
			packet.setMsg(msg);
			unicastMsg.pushPacket(packet);
			
		}
		System.out.println("Sending Messages");
		new Thread(unicastMsg).start();
	}
	


}