package com.distsc.app;

import java.util.ArrayList;

import com.distsc.beans.Node;
import com.distsc.chat.server.ClientServer;
import com.distsc.intercluster.outbound.InterClusterOutboundConnection;
import com.distsc.intercluster.server.InterClusterServer;
import com.distsc.node.outbound.OutboundConnection;
import com.distsc.node.server.Server;
import com.distsc.raft.RAFTStatus;
import com.distsc.raft.algorithm.RAFT;

public class RunMain
{

	public static void main(String args[])
	{
		
		ArrayList<Node> internodes=new ArrayList<Node>();
		internodes.add(new Node("Server1","192.168.0.14",8282));
		ClusterConfiguration.setNodes(internodes);
		
		GlobalConfiguration.setCurrentNode(new Node("Server1", "192.168.0.1", 8992));
		
		///Start InterCluster Server
		new Thread(new InterClusterServer()).start();
		
		//Start Outbound Server
		new Thread(new InterClusterOutboundConnection() ).start();
		
		
		//Starting Server Thread.
		new Thread(new Server()).start();
		
		//Starting Clinet Thread.

		new Thread(new OutboundConnection()).start();
		
		//Starting ExternalClietn Thread.

		new Thread(new ClientServer()).start();
		
		
		//Starting Receiver Current Host Information.
		
		
		//Setting RAFT Time.
		

		ArrayList<Node> nodes=new ArrayList<Node>();
		nodes.add(new Node("Server2","192.168.0.2",8992));
		nodes.add(new Node("Server3","192.168.0.3",8992));
		
		GlobalConfiguration.setNodes(nodes);
		
		RAFTStatus.raftTimerInit();
		new Thread(new RAFT()).start();

		
		
	}
	
}
