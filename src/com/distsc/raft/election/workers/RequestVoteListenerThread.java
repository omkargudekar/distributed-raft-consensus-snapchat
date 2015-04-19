package com.distsc.raft.election.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.RequestVoteMsgQueue;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.NodeChannelContextMap;
import com.distsc.raft.RAFTStatus;

public class RequestVoteListenerThread implements Runnable
{
	
	static Logger logger = LoggerFactory.getLogger(RequestVoteListenerThread.class);

	@Override
	public void run()
	{
		logger.info("RequestVoteListenerThread  Started");
		while (true)
		{

			switch (RAFTStatus.getCurrentNodeState())
			{
			case Leader:
				pause();
				break;

			case Candidate:
				pause();
				break;

			case Follower:
				vote();
				break;

			default:
				pause();
				break;
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

	private void vote()
	{

		if (RequestVoteMsgQueue.getCount() > 0)
		{
			RequestContext requestContext = RequestVoteMsgQueue.pop();
			Request msg = null;
			if (RAFTStatus.hasVoted() == false && RAFTStatus.getDeclaredLeader() == null)
			{

				logger.info("Voting YES to Condidate " + requestContext.getRequest().getPayload().getRequestVote().getCandidateId());
				RAFTStatus.setVoted(true);
				RAFTStatus.setVotedFor(requestContext.getRequest().getPayload().getRequestVote().getCandidateId());
				msg = Request.newBuilder().setMessageHeader(Request.MessageHeader.RequestVoteResultMsg).setPayload(MessageProto.Payload.newBuilder().setRequestVoteResult(MessageProto.RequestVoteResult.newBuilder().setSenderNodeId(GlobalConfiguration.getCurrentNode().getNodeID()).setVoteGranted(true).setTerm(RAFTStatus.getCurrentTerm()))).build();
				NodeChannelContextMap.getNodeContext(requestContext.getRequest().getPayload().getRequestVote().getCandidateId()).writeAndFlush(msg);
			}
			else if (RAFTStatus.hasVoted() == true)
			{
				logger.info("Already Voted.Voting NO to Candidate " + requestContext.getRequest().getPayload().getRequestVote().getCandidateId());
				msg = Request.newBuilder().setMessageHeader(Request.MessageHeader.RequestVoteResultMsg).setPayload(MessageProto.Payload.newBuilder().setRequestVoteResult(MessageProto.RequestVoteResult.newBuilder().setSenderNodeId(GlobalConfiguration.getCurrentNode().getNodeID()).setVoteGranted(false).setTerm(RAFTStatus.getCurrentTerm()))).build();
				NodeChannelContextMap.getNodeContext(requestContext.getRequest().getPayload().getRequestVote().getCandidateId()).writeAndFlush(msg);
			}
		}
		else
		{
			pause();
		}
	}

}
