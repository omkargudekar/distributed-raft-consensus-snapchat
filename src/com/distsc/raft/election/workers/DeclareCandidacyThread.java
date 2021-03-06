package com.distsc.raft.election.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.comm.msg.queues.AppendEntriesQueue;
import com.distsc.comm.msg.queues.AppendEntriesResultQueue;
import com.distsc.comm.msg.queues.RequestVoteMsgQueue;
import com.distsc.comm.msg.queues.RequestVoteResultMsgQueue;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.raft.RAFTStatus;
import com.distsc.server.ServerMulticast;

public class DeclareCandidacyThread implements Runnable
{

	static Logger logger = LoggerFactory.getLogger(DeclareCandidacyThread.class);

	@Override
	public void run()
	{
		logger.info("DeclareCandidacyThread Started");
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
				nominate();
				break;

			default:
				pause();
				break;
			}
		}
	}

	private void nominate()
	{
		RAFTTimeout();
		if(RAFTStatus.getCurrentNodeState()==RAFTStatus.NodeState.Follower && RAFTStatus.getDeclaredLeader()==null && RAFTStatus.hasVoted()==false)
		{
			logger.info("Declaring Candidacy...");
			RAFTStatus.setVoted(true);
			RAFTStatus.setTotalVotes(1);
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Candidate);
			ServerMulticast followerMulticast=new ServerMulticast();
			
			Request msg=Request.newBuilder().setMessageHeader(Request.MessageHeader.RequestVoteMsg)
												.setPayload(MessageProto.Payload.newBuilder().setRequestVote(MessageProto.RequestVote.newBuilder()
														.setCandidateId(GlobalConfiguration.getCurrentNode().getNodeID())
														.setTerm(RAFTStatus.getCurrentTerm()))).build();
			followerMulticast.send(msg);
			RAFTTimeout();
			if(!isLeader())
			{
				reset();
			}
			
			
		}
	}
	
	public boolean isLeader()
	{
		
		if(RAFTStatus.getCurrentNodeState()==RAFTStatus.NodeState.Leader)
		{
			return true;
		
		}
		return false;
	}
	
	public void reset()
	{
		RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Follower);
		RequestVoteMsgQueue.reset();
		RequestVoteResultMsgQueue.reset();
		AppendEntriesQueue.reset();
		AppendEntriesResultQueue.reset();
		RAFTStatus.setVoted(false);

	}


	private void RAFTTimeout()
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
