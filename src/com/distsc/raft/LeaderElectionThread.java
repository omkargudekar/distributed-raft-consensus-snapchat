package com.distsc.raft;

import com.distsc.raft.election.workers.DeclareCandidacyThread;
import com.distsc.raft.election.workers.HeartbeatSenderThread;
import com.distsc.raft.election.workers.LogAppendListener;
import com.distsc.raft.election.workers.LogAppendResultListener;
import com.distsc.raft.election.workers.RequestVoteListenerThread;
import com.distsc.raft.election.workers.RequestVoteResultListenerThread;

public class LeaderElectionThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("Starting leader election threads...");
		
		new Thread(new DeclareCandidacyThread()).start();
	    new Thread(new RequestVoteResultListenerThread()).start();
		new Thread(new RequestVoteListenerThread()).start();
		new Thread(new LogAppendListener()).start();
		new Thread(new LogAppendResultListener()).start();
		new Thread(new HeartbeatSenderThread()).start();
		
	}

}
