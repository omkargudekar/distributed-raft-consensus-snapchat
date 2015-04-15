package com.distsc.raft.election.workers;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.RequestVoteResultMsgQueue;
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
				System.out.println("Received YES vote from :"+context.getRequest().getPayload().getRequestVoteResult().getSenderNodeId());

				RAFTStatus.setTotalVotes(RAFTStatus.getTotalVotes()+1);
			}
			else
			{
				System.out.println("Received NO vote from :"+context.getRequest().getPayload().getRequestVoteResult().getSenderNodeId());

			}
		}
		if (RAFTStatus.getTotalVotes() > (GlobalConfiguration.getNetwotkSize()/2) )
		{	
			System.out.println("****************************");
			System.out.println("*     Elected As Leader    *");
			System.out.println("****************************");
			RAFTStatus.setDeclaredLeader(GlobalConfiguration.getCurrentNode().getNodeID());
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Leader);
			RAFTStatus.reset();
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
