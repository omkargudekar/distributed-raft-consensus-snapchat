package com.distributedsnapchat.raft;

import com.distributedsnapchat.raft.election.workers.ElectionParticipationThread;
import com.distributedsnapchat.raft.election.workers.HeartbeatListenerThread;
import com.distributedsnapchat.raft.election.workers.HeartbeatSenderThread;
import com.distributedsnapchat.raft.election.workers.NominationListenerThread;
import com.distributedsnapchat.raft.election.workers.VoteListenerThread;
import com.distributedsnapchat.raft.logreplication.workers.FollowerReplicationRequestListener;
import com.distributedsnapchat.raft.logreplication.workers.LeaderReplicationRequestThread;

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
		new  Thread(new FollowerReplicationRequestListener()).start();
		new  Thread(new LeaderReplicationRequestThread()).start();
		
	}

}
