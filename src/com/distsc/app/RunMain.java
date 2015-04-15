package com.distsc.app;
import com.distsc.app.config.ClusterConfigReader;
import com.distsc.chat.server.ChatServer;
import com.distsc.network.OutboundConnection;
import com.distsc.node.server.Server;
import com.distsc.raft.algorithm.RAFT;

public class RunMain
{

	public static void main(String args[])
	{
		//Read & Setup Cluster Configuration
		ClusterConfigReader.readAndSetUp("config/cluster.conf");
		
		
		
		//Starting Server Thread.
		new Thread(new Server()).start();
		//Starting Client Thread.
		new Thread(new OutboundConnection()).start();
		
		//Starting ChatServer Thread.
		new Thread(new ChatServer()).start();
		
				
		
		//Setting RAFT Thread.
		
		new Thread(new RAFT()).start();

		
		
	}
	
}
