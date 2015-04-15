package com.distsc.raft.algorithm;

import com.distsc.raft.LeaderElectionThread;
import com.distsc.raft.RAFTStatus;


public class RAFT implements Runnable
{

	@Override
	public void run()
	{
		RAFTStatus.raftTimerInit();		
		new Thread(new LeaderElectionThread()).start();

	}

}


