package com.distsnapchat.communication;


import java.util.ArrayList;

import org.dissnapchat.protobuf.MessageProto.Message;

import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.beans.Node;

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
		for(Node node : RAFTStatus.getNodes())
		{
			System.out.println("Sending Message to "+node);
			new Thread(new UnicastMessage(node.getNodeIP(),node.getNodePort(),msg)).start();
		}
	}
	


}