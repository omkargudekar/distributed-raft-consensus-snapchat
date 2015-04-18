package com.distsc.app;
import com.distc.cluster.worker.InterClusterThreads;
import com.distsc.app.config.ConfigReader;
import com.distsc.comm.msg.queues.workers.ClusterThreadPool;

public class RunMain
{

	public static void main(String args[])
	{
		
		if(args[0]==null || args[0].trim().equals(""))
		{
			System.out.println("No Configuration File Parameter");
			System.exit(0);
		}
		else
		{
				ConfigReader.setUp(args[0]);
				new Thread(new ClusterThreadPool()).start();
				new Thread(new InterClusterThreads()).start();
		}		
	}
	
}
