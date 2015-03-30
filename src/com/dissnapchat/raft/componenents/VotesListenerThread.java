package com.dissnapchat.raft.componenents;

import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.communication.buffers.VoteBuffer;

public class VotesListenerThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("VoteCounterThread started...");
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
		if (VoteBuffer.getNodeVoteCount() > (RAFTStatus.getNetwotkSize() / 2) )
		{	
			System.out.println("****  Elected As Leader ****");
			RAFTStatus.setDeclaredLeader(RAFTStatus.getCurrentNode());
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Leader);
		}
	}
	
	
	private void pause()
	{
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}
	

}
