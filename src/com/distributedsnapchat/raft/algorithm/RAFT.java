package com.distributedsnapchat.raft.algorithm;

import com.distributedsnapchat.raft.LeaderElectionThread;


public class RAFT implements Runnable
{

	@Override
	public void run()
	{
		
		new Thread(new LeaderElectionThread()).start();

	}

}


