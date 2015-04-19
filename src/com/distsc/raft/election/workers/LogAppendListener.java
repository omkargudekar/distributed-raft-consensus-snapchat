package com.distsc.raft.election.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.AppendEntriesQueue;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.NodeChannelContextMap;
import com.distsc.raft.RAFTStatus;

public class LogAppendListener implements Runnable
{

	static Logger logger = LoggerFactory.getLogger(HeartbeatSenderThread.class);

	@Override
	public void run()
	{
		logger.info("LogAppendListener Thread Started");

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
			logger.info("Leader Conflict. Checking Logs");
			RequestContext requestContext;
				requestContext = AppendEntriesQueue.pop();
				logger.info("Leader found with Term : " + requestContext.getRequest().getPayload().getAppendEntries().getTerm());
				if (requestContext.getRequest().getPayload().getAppendEntries().getTerm() > RAFTStatus.getCurrentTerm())
				{
					logger.info("Stale Log.Changing State from Leader to Follower.");
					RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Follower);
					RAFTStatus.setDeclaredLeader(requestContext.getRequest().getPayload().getAppendEntries().getLeaderId());
					RAFTStatus.reset();
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
			logger.info("Leader Conflict. Checking Logs");
			RequestContext requestContext;
				requestContext = AppendEntriesQueue.pop();
				logger.info("Leader found with Term : " + requestContext.getRequest().getPayload().getAppendEntries().getTerm());
				if (requestContext.getRequest().getPayload().getAppendEntries().getTerm() > RAFTStatus.getCurrentTerm())
				{
					logger.info("Stale Log.Changing State from Leader to Follower.");
					RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Follower);
					RAFTStatus.setDeclaredLeader(requestContext.getRequest().getPayload().getAppendEntries().getLeaderId());
					RAFTStatus.reset();
					
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
		logger.info("AppendEntriesQueue Count:"+AppendEntriesQueue.getCount()+" , Leader : "+RAFTStatus.getDeclaredLeader());
		if (AppendEntriesQueue.getCount() == 0 && RAFTStatus.getDeclaredLeader() != null)
		{
			logger.info("Heartbeat Missed.");
			RAFTStatus.setVoted(false);
			RAFTStatus.setDeclaredLeader(null);
		}
		else if (AppendEntriesQueue.getCount() > 0 && RAFTStatus.getDeclaredLeader() != null)
		{
				RequestContext requestContext = AppendEntriesQueue.pop();
				logger.info("Heartbeat Received : Leader Id : " + requestContext.getRequest().getPayload().getAppendEntries().getLeaderId() + " Leader Term :" + requestContext.getRequest().getPayload().getAppendEntries().getTerm());
				Request msg = Request.newBuilder().setMessageHeader(Request.MessageHeader.AappendEntriesResultMsg).setPayload(MessageProto.Payload.newBuilder().setAppendEntriesresult(MessageProto.AppendEntriesResult.newBuilder().setTerm(RAFTStatus.getCurrentTerm()).setSuccess(true))).build();
				NodeChannelContextMap.getNodeContext(requestContext.getRequest().getPayload().getAppendEntries().getLeaderId()).writeAndFlush(msg);
				RAFTStatus.setCurrentTerm(requestContext.getRequest().getPayload().getAppendEntries().getTerm());
				AppendEntriesQueue.reset();
		}
		else if(AppendEntriesQueue.getCount() > 0 && RAFTStatus.getDeclaredLeader() == null)
		{
			RequestContext requestContext = AppendEntriesQueue.pop();
			RAFTStatus.setDeclaredLeader(requestContext.getRequest().getPayload().getAppendEntries().getLeaderId());
			logger.info("Heartbeat Received : Leader Id : " + requestContext.getRequest().getPayload().getAppendEntries().getLeaderId() + " Leader Term :" + requestContext.getRequest().getPayload().getAppendEntries().getTerm());
			Request msg = Request.newBuilder().setMessageHeader(Request.MessageHeader.AappendEntriesResultMsg).setPayload(MessageProto.Payload.newBuilder().setAppendEntriesresult(MessageProto.AppendEntriesResult.newBuilder().setTerm(RAFTStatus.getCurrentTerm()).setSuccess(true))).build();
			NodeChannelContextMap.getNodeContext(requestContext.getRequest().getPayload().getAppendEntries().getLeaderId()).writeAndFlush(msg);
			RAFTStatus.setCurrentTerm(requestContext.getRequest().getPayload().getAppendEntries().getTerm());
		}
		pause();
		
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
