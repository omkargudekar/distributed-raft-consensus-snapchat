package com.distsc.raft;



public class RAFT implements Runnable
{

	@Override
	public void run()
	{
		RAFTStatus.raftTimerInit();		
		new Thread(new LeaderElectionThread()).start();

	}

}


