package com.distsc.raft.election.workers;

import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.AppendEntriesQueue;
import com.distsc.comm.msg.queues.AppendEntriesResultQueue;
import com.distsc.raft.RAFTStatus;

public class LogAppendResultListener implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("LogAppendRequestListener Thread Started");

		while (true)
		{
			
			
			
			switch (RAFTStatus.getCurrentNodeState())
			{
			case Leader:
				checkLogAppendResult();
				break;

			case Candidate:
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
	
	

	
	public void checkLogAppendResult()
	{
		if (AppendEntriesResultQueue.getCount() >0)
		{
			RequestContext requestContext;
			for(int counter=0;counter<AppendEntriesQueue.getCount();counter++)
			{
				requestContext=AppendEntriesQueue.pop();
				System.out.println("AppendEntriesResult Received From \nNode: "+requestContext.getRequest().getPayload().getAppendEntriesresult().getSenderNodeId()+
									" \n TermId: "+requestContext.getRequest().getPayload().getAppendEntriesresult().getTerm()+
									" \n Success: "+requestContext.getRequest().getPayload().getAppendEntriesresult().getSuccess());
				
			}
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
