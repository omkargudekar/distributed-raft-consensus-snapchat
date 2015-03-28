package com.distsnapchat.app;

import java.util.ArrayList;

import com.distsnapchat.beans.Node;

import comm.dissnapchat.raft.RAFTStatus;
import comm.dissnapchat.raft.algorithm.RAFT;

public class RunMain
{

	public static void main(String args[])
	{
		RAFTStatus.setServerID("Server1");
		ArrayList<Node> nodes=new ArrayList<Node>();
		nodes.add(new Node("Server2","192.168.1.2",8080));

		RAFTStatus.setNodes(nodes);
		
		new Thread(new RAFT()).start();

		
		
	}
	
}
