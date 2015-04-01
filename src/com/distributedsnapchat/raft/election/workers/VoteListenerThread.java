package com.distributedsnapchat.raft.election.workers;

import com.distributedsnapchat.communication.buffers.HeartbeatBuffer;
import com.distributedsnapchat.communication.buffers.NominationsBuffer;
import com.distributedsnapchat.communication.buffers.VoteBuffer;
import com.distributedsnapchat.raft.RAFTStatus;

public class VoteListenerThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("VoteListenerThread started...");
		while (true)
		{
			while (true)
			{

				switch (RAFTStatus.getCurrentNodeState())
				{
				case Leader:
					pause();
					break;

				case Candidate:
					checkVotes();
					break;

				case OrphanFollower:
					pause();
					break;

				case Follower:
					pause();
					break;

				default:
					pause();
					break;
				}
			}
			
			
			

			
		}
	}
	
	public void checkVotes()
	{
		if (VoteBuffer.getNodeVoteCount() > (RAFTStatus.getNetwotkSize()/2) )
		{	
			System.out.println("****  Elected As Leader ****");
			RAFTStatus.setDeclaredLeader(RAFTStatus.getCurrentNode());
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Leader);
			VoteBuffer.reset();
			NominationsBuffer.reset();
			HeartbeatBuffer.reset();
		}
	
	}
	
	
	private void pause()
	{
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}
	

}
