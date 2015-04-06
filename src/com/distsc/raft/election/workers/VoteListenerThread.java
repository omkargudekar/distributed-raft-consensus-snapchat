package com.distsc.raft.election.workers;

import com.distsc.app.GlobalConfiguration;
import com.distsc.comm.msg.queues.inbound.HeartbeatBuffer;
import com.distsc.comm.msg.queues.inbound.NominationsBuffer;
import com.distsc.comm.msg.queues.inbound.VoteBuffer;
import com.distsc.raft.RAFTStatus;

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
		if (VoteBuffer.getNodeVoteCount() > (GlobalConfiguration.getNetwotkSize()/2) )
		{	
			System.out.println("****  Elected As Leader ****");
			RAFTStatus.setDeclaredLeader(GlobalConfiguration.getCurrentNode());
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Leader);
			VoteBuffer.reset();
			NominationsBuffer.reset();
			HeartbeatBuffer.reset();
			RAFTStatus.setVoted(false);
		}
		else 
		{
	
			
				
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
