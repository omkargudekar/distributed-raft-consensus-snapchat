package com.distsc.app;

import java.util.ArrayList;

import com.distsc.beans.Node;
import com.distsc.comm.client.ClientConnection;
import com.distsc.comm.server.Server;
import com.distsc.comm.server.external.ExternalClientServer;
import com.distsc.raft.RAFTStatus;
import com.distsc.raft.algorithm.RAFT;

public class RunMain
{

	public static void main(String args[])
	{
		//Starting Server Thread.
		new Thread(new Server()).start();
		
		//Starting Clinet Thread.

		new Thread(new ClientConnection()).start();
		
		//Starting ExternalClietn Thread.

		new Thread(new ExternalClientServer()).start();
		
		
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
