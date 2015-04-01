package com.distributedsnapchat.app;

import java.util.ArrayList;

import com.distributedsnapchat.beans.Node;
import com.distributedsnapchat.communication.client.receiver.ClientReceiver;
import com.distributedsnapchat.communication.nodes.receiver.NodeReceiver;
import com.distributedsnapchat.raft.RAFTStatus;
import com.distributedsnapchat.raft.algorithm.RAFT;

public class RunMain
{

	public static void main(String args[])
	{
		//Starting Receiver Thread.
		new Thread(new ClientReceiver()).start();	
		new Thread(new NodeReceiver()).start();
		
		//Starting Receiver Current Host Information.
		GlobalConfiguration.setCurrentNode(new Node("Server1", "192.168.0.1", 8992));
		
		//Setting RAFT Time.
		

		ArrayList<Node> nodes=new ArrayList<Node>();
		nodes.add(new Node("Server2","192.168.0.2",8992));
		nodes.add(new Node("Server3","192.168.0.3",8992));
		
		GlobalConfiguration.setNodes(nodes);
		
		RAFTStatus.raftTimerInit();
		new Thread(new RAFT()).start();

		
		
	}
	
}
