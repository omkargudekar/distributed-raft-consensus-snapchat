package com.distsc.raft.election.workers;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.inbound.RequestVoteResultMsgQueue;
import com.distsc.raft.RAFTStatus;

public class RequestVoteResultListenerThread implements Runnable
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
		RequestContext context=null;
		for(int counter=0;counter<RequestVoteResultMsgQueue.getCount();counter++)
		{
			context=RequestVoteResultMsgQueue.pop();
			
			if(context.getRequest().getPayload().getRequestVoteResult().getVoteGranted()==true)
			{
				GlobalConfiguration.setTotalVotes(GlobalConfiguration.getTotalVotes()+1);
			}
			
		}
		if (GlobalConfiguration.getTotalVotes() > (GlobalConfiguration.getNetwotkSize()/2) )
		{	
			System.out.println("****  Elected As Leader ****");
			RAFTStatus.setDeclaredLeader(GlobalConfiguration.getCurrentNode().getNodeID());
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Leader);
			GlobalConfiguration.reset();
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
