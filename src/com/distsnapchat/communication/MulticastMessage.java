package com.distsnapchat.communication;


import java.util.ArrayList;

import com.distsnapchat.beans.Node;

public class MulticastMessage
{

	public  void send(ArrayList<Node> nodes, String message)
	{	
		
		for(Node node : nodes)
		{
			System.out.println("Sending Message...");
			
			new Thread(new UnicastMessage(node.getNodeIP(),node.getNodePort(),message)).start();
		}
		
	}
	


}