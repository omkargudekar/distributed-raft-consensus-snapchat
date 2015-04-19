package com.distsc.raft.election.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.RequestVoteResultMsgQueue;
import com.distsc.raft.RAFTStatus;

public class RequestVoteResultListenerThread implements Runnable
{

	static Logger logger = LoggerFactory.getLogger(RequestVoteListenerThread.class);

	@Override
	public void run()
	{
		logger.info("RequestVoteResultListenerThread started...");

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
				logger.info("Received YES vote from :"+context.getRequest().getPayload().getRequestVoteResult().getSenderNodeId());

				RAFTStatus.setTotalVotes(RAFTStatus.getTotalVotes()+1);
			}
			else
			{
				logger.info("Received NO vote from :"+context.getRequest().getPayload().getRequestVoteResult().getSenderNodeId());

			}
		}
		if (RAFTStatus.getTotalVotes() > (GlobalConfiguration.getNetwotkSize()/2) )
		{	
			logger.info("****************************");
			logger.info("*     Elected As Leader    *");
			RAFTStatus.setDeclaredLeader(GlobalConfiguration.getCurrentNode().getNodeID());
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Leader);
			RAFTStatus.setCurrentTerm(RAFTStatus.getCurrentTerm()+1);
			logger.info("TERM : "+RAFTStatus.getCurrentTerm());
			RAFTStatus.reset();
			logger.info("****************************");
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
