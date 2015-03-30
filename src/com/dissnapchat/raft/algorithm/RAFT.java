package com.dissnapchat.raft.algorithm;

import com.dissnapchat.raft.election.componenents.LeaderElectionThread;


public class RAFT implements Runnable
{

	@Override
	public void run()
	{
		
		new Thread(new LeaderElectionThread()).start();

	}

}


