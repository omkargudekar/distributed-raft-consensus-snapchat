package com.distsnapchat.app;

import java.util.ArrayList;

import com.dissnapchat.raft.RAFTStatus;
import com.dissnapchat.raft.algorithm.RAFT;
import com.distsnapchat.beans.Node;
import com.distsnapchat.communication.receiver.Receiver;

public class RunMain
{

	public static void main(String args[])
	{
		//Starting Receiver Thread.
		new Thread(new Receiver()).start();
		
		
		//Starting Receiver Current Host Information.
		RAFTStatus.setCurrentNode(new Node("Server1", "192.168.0.1", 8992));
		
		//Setting RAFT Time.
		RAFTStatus.raftTimerInit();

		
		ArrayList<Node> nodes=new ArrayList<Node>();
		nodes.add(new Node("Server2","192.168.0.2",8992));
		nodes.add(new Node("Server3","192.168.0.3",8992));
		RAFTStatus.setNodes(nodes);
		
		new Thread(new RAFT()).start();

		
		
	}
	
}
