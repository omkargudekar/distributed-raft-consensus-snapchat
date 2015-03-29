package com.distsnapchat.app;

import java.util.ArrayList;

import com.distsnapchat.beans.Node;
import com.distsnapchat.communication.receiver.Receiver;

import comm.dissnapchat.raft.RAFTStatus;
import comm.dissnapchat.raft.algorithm.RAFT;

public class RunMain
{

	public static void main(String args[])
	{

		new Thread(new Receiver()).start();
		
		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		RAFTStatus.setUpCurrentHost("Server1", "192.168.0.1", 8992);
		
		
		RAFTStatus.raftTimerInit();

		
		ArrayList<Node> nodes=new ArrayList<Node>();
		
		nodes.add(new Node("Server2","192.168.0.2",8992));
		nodes.add(new Node("Server3","192.168.0.3",8992));

		RAFTStatus.setNodes(nodes);
		
		new Thread(new RAFT()).start();

		
		
	}
	
}
