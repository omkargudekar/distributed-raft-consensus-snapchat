package com.distsc.raft.election.workers;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.inbound.AppendEntriesQueue;
import com.distsc.comm.msg.queues.inbound.AppendEntriesResultQueue;
import com.distsc.comm.msg.queues.inbound.RequestVoteMsgQueue;
import com.distsc.comm.msg.queues.inbound.RequestVoteResultMsgQueue;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.NetworkContextMap;
import com.distsc.raft.RAFTStatus;

public class LogAppendListener implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("HeartbeatListenerThread Thread Started");

		while (true)
		{
			
			
			
			switch (RAFTStatus.getCurrentNodeState())
			{
			case Leader:
				checkForNetworkPartition();
				break;

			case Candidate:
				checkIfLeaderExist();
				break;

			case Follower:
				checkHeartbeat();
				break;

			default:
				pause();
				break;
			}
		
		}

	}
	
	
	public void checkIfLeaderExist()
	{
		starRAFTTimer();
		if (AppendEntriesQueue.getCount() >0)
		{
			RequestContext requestContext;
			for(int counter=0;counter<AppendEntriesQueue.getCount();counter++)
			{
				requestContext=AppendEntriesQueue.pop();
				if(requestContext.getRequest().getPayload().getAppendEntries().getTerm() < GlobalConfiguration.getCurrentTerm() )
				{
					RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Follower);
					RAFTStatus.setDeclaredLeader(requestContext.getRequest().getPayload().getAppendEntries().getLeaderId());
					GlobalConfiguration.reset();

				}
			}
		
		}

		
		
	}

	
	public void checkForNetworkPartition()
	{
		if (AppendEntriesQueue.getCount() >0)
		{
			RequestContext requestContext;
			for(int counter=0;counter<AppendEntriesQueue.getCount();counter++)
			{
				requestContext=AppendEntriesQueue.pop();
				if(requestContext.getRequest().getPayload().getAppendEntries().getTerm() < GlobalConfiguration.getCurrentTerm() )
				{
					RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Follower);
					RAFTStatus.setDeclaredLeader(requestContext.getRequest().getPayload().getAppendEntries().getLeaderId());
					
				}
			}
		
		}
	}
	public void checkHeartbeat()
	{
		starRAFTTimer();
		if (AppendEntriesQueue.getCount() == 0)
		{
			RAFTStatus.setDeclaredLeader(null);
		}
		else
		{
			
			RequestContext requestContext=AppendEntriesQueue.pop();
			Request msg=null;

			msg=Request.newBuilder().setMessageHeader(Request.MessageHeader.AappendEntriesResultMsg)
					.setPayload(MessageProto.Payload.newBuilder().setAppendEntriesresult(MessageProto.AppendEntriesResult.newBuilder()
							.setTerm(GlobalConfiguration.getCurrentTerm())
							.setSuccess(true)
							)).build();

			NetworkContextMap.getNodeContext(requestContext.getRequest().getPayload().getAppendEntries().getLeaderId()).
			writeAndFlush(msg);
		}
	}
	
	public void starRAFTTimer()
	{
		try
		{
			Thread.sleep(RAFTStatus.getRaftTimer());
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
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
