package com.distsc.raft.algorithm;

import com.distsc.raft.LeaderElectionThread;


public class RAFT implements Runnable
{

	@Override
	public void run()
	{
		
		new Thread(new LeaderElectionThread()).start();

	}

}


