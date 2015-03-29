package com.dissnapchat.raft.componenents;

public class LeaderElectionThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("Starting leader election threads...");
		
		new Thread(new NominationThread()).start();
	    new Thread(new VotesListenerThread()).start();
		new Thread(new CandidacyListenerThread()).start();
		new Thread(new HeartbeatListenerThread()).start();
		new Thread(new HeartbeatSenderThread()).start();
		
	}

}
