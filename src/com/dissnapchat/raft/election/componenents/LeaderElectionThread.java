package com.dissnapchat.raft.election.componenents;

public class LeaderElectionThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("Starting leader election threads...");
		
		new Thread(new ElectionParticipationThread()).start();
	    new Thread(new VoteListenerThread()).start();
		new Thread(new NominationListenerThread()).start();
		new Thread(new HeartbeatListenerThread()).start();
		new Thread(new HeartbeatSenderThread()).start();
		
	}

}
