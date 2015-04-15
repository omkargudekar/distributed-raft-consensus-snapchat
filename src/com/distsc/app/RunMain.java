package com.distsc.app;
import com.distsc.app.config.ClusterConfigReader;
import com.distsc.comm.msg.queues.workers.QueueWorkerThreads;
import com.distsc.network.maps.discovery.NodeDiscoveryThread;
import com.distsc.raft.RAFT;
import com.distsc.server.Server;
import com.distsc.server.listener.ListenerThread;

public class RunMain
{

	public static void main(String args[])
	{
		//Read & Setup Cluster Configuration
		ClusterConfigReader.readAndSetUp("runtime/cluster.conf");
		

		//Starting Server Thread.
		new Thread(new Server()).start();
		
		//Starting ServerListener Thread.
		new Thread(new ListenerThread()).start();
		
		//Starting QueueWorker Threads
		new Thread(new QueueWorkerThreads()).start();
		
		
		//Starting NetworkDiscovery Thread
		new Thread(new NodeDiscoveryThread()).start();

		
		//Setting RAFT Thread.
		new Thread(new RAFT()).start();

		
		
	}
	
}
