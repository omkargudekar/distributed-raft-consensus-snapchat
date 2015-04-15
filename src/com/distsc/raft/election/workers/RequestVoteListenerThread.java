package com.distsc.raft.election.workers;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.AppendEntriesQueue;
import com.distsc.comm.msg.queues.RequestVoteMsgQueue;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.NodeChannelContextMap;
import com.distsc.raft.RAFTStatus;

public class RequestVoteListenerThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("RequestVoteListenerThread  Started");
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

	private  void vote()
	{
		
		RequestContext requestContext=AppendEntriesQueue.pop();
		Request msg=null;
		if (RequestVoteMsgQueue.getCount() > 0 &&  RAFTStatus.hasVoted()==false && RAFTStatus.getDeclaredLeader()==null)
		{

			System.out.println("Voting YES to Condidate "+requestContext.getRequest().getPayload().getRequestVote().getCandidateId());
			RAFTStatus.setVoted(true);
				msg=Request.newBuilder().setMessageHeader(Request.MessageHeader.RequestVoteResultMsg)
					.setPayload(MessageProto.Payload.newBuilder().setRequestVoteResult(MessageProto.RequestVoteResult.newBuilder()
							.setSenderNodeId(GlobalConfiguration.getCurrentNode().getNodeID())
							.setVoteGranted(true)
							.setTerm(RAFTStatus.getCurrentTerm()))).build();

		}
		else if(RequestVoteMsgQueue.getCount() > 0 &&  RAFTStatus.hasVoted()==true)
		{
			System.out.println("Already Voted.Voting NO to Candidate "+requestContext.getRequest().getPayload().getRequestVote().getCandidateId());

			msg=Request.newBuilder().setMessageHeader(Request.MessageHeader.RequestVoteResultMsg)
					.setPayload(MessageProto.Payload.newBuilder().setRequestVoteResult(MessageProto.RequestVoteResult.newBuilder()
							.setSenderNodeId(GlobalConfiguration.getCurrentNode().getNodeID())
							.setVoteGranted(false)
							.setTerm(RAFTStatus.getCurrentTerm()))).build();
		}
		NodeChannelContextMap.getNodeContext(requestContext.getRequest().getPayload().getRequestVote().getCandidateId()).
		writeAndFlush(msg);

	}
	
	

}
