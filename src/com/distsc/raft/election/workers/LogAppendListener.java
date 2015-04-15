package com.distsc.raft.election.workers;

import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.AppendEntriesQueue;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.NodeChannelContextMap;
import com.distsc.raft.RAFTStatus;

public class LogAppendListener implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("LogAppendListener Thread Started");

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
		if (AppendEntriesQueue.getCount() > 0)
		{
			System.out.println("Leader Conflict. Checking Logs");
			RequestContext requestContext;
			for (int counter = 0; counter < AppendEntriesQueue.getCount(); counter++)
			{
				requestContext = AppendEntriesQueue.pop();
				System.out.println("Leader found with Tearm : " + requestContext.getRequest().getPayload().getAppendEntries().getTerm());
				if (requestContext.getRequest().getPayload().getAppendEntries().getTerm() < RAFTStatus.getCurrentTerm())
				{
					System.out.println("Stale Log.Changing State from Leader to Follower.");

					RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Follower);
					RAFTStatus.setDeclaredLeader(requestContext.getRequest().getPayload().getAppendEntries().getLeaderId());
					RAFTStatus.reset();
					break;

				}
			}

		}
		else
		{
			pause();
		}

	}

	public void checkForNetworkPartition()
	{
		if (AppendEntriesQueue.getCount() > 0)
		{
			System.out.println("Leader Conflict. Checking Logs");
			RequestContext requestContext;
			for (int counter = 0; counter < AppendEntriesQueue.getCount(); counter++)
			{
				requestContext = AppendEntriesQueue.pop();
				System.out.println("Leader found with Tearm : " + requestContext.getRequest().getPayload().getAppendEntries().getTerm());
				if (requestContext.getRequest().getPayload().getAppendEntries().getTerm() < RAFTStatus.getCurrentTerm())
				{
					System.out.println("Stale Log.Changing State from Leader to Follower.");
					RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Follower);
					RAFTStatus.setDeclaredLeader(requestContext.getRequest().getPayload().getAppendEntries().getLeaderId());
					RAFTStatus.reset();
					break;
				}
			}

		}
		else
		{
			pause();
		}
	}

	public void checkHeartbeat()
	{
		starRAFTTimer();
		if (AppendEntriesQueue.getCount() == 0 && RAFTStatus.getDeclaredLeader() != null)
		{
			System.out.println("Heartbeat Missed.");
			RAFTStatus.setDeclaredLeader(null);
		}
		else if (AppendEntriesQueue.getCount() > 0)
		{		RequestContext requestContext = AppendEntriesQueue.pop();
				Request msg = null;
				System.out.println("Heartbeat Received : Leader Id : " 
								+requestContext.getRequest().getPayload().getAppendEntries().getLeaderId()
								+" Leader Term :"
								+ requestContext.getRequest().getPayload().getAppendEntries().getTerm());
				msg = Request.newBuilder().setMessageHeader(Request.MessageHeader.AappendEntriesResultMsg).setPayload(MessageProto.Payload.newBuilder().setAppendEntriesresult(MessageProto.AppendEntriesResult.newBuilder().setTerm(RAFTStatus.getCurrentTerm()).setSuccess(true))).build();
				NodeChannelContextMap.getNodeContext(requestContext.getRequest().getPayload().getAppendEntries().getLeaderId()).writeAndFlush(msg);
				AppendEntriesQueue.reset();
		}
		else
		{
			pause();
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
