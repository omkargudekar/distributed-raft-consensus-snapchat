package com.distsc.raft;

import com.distsc.raft.election.workers.DeclareCandidacyThread;
import com.distsc.raft.election.workers.HeartbeatListenerThread;
import com.distsc.raft.election.workers.HeartbeatSenderThread;
import com.distsc.raft.election.workers.NominationListenerThread;
import com.distsc.raft.election.workers.VoteListenerThread;
import com.distsc.raft.logreplication.workers.FollowerReplicationRequestListener;
import com.distsc.raft.logreplication.workers.LeaderReplicationRequestThread;

public class LeaderElectionThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("Starting leader election threads...");
		
		new Thread(new DeclareCandidacyThread()).start();
	    new Thread(new VoteListenerThread()).start();
		new Thread(new NominationListenerThread()).start();
		new Thread(new HeartbeatListenerThread()).start();
		new Thread(new HeartbeatSenderThread()).start();
		new  Thread(new FollowerReplicationRequestListener()).start();
		new  Thread(new LeaderReplicationRequestThread()).start();
		
	}

}
