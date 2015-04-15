package com.distsc.raft.election.workers;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.comm.msg.queues.inbound.AppendEntriesQueue;
import com.distsc.comm.msg.queues.inbound.AppendEntriesResultQueue;
import com.distsc.comm.msg.queues.inbound.RequestVoteMsgQueue;
import com.distsc.comm.msg.queues.inbound.RequestVoteResultMsgQueue;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.raft.RAFTStatus;
import com.distsc.server.FollowerMulticast;

public class DeclareCandidacyThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("ElectionLeaderParticipationThread Started");
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
		waitForRAFTTimeOut();
		if(RAFTStatus.getCurrentNodeState()==RAFTStatus.NodeState.Follower && RAFTStatus.getDeclaredLeader()==null)
		{
			RAFTStatus.setVoted(true);
			System.out.println("Declaring Candidacy...");
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Candidate);
			FollowerMulticast followerMulticast=new FollowerMulticast();
			
			Request msg=Request.newBuilder().setMessageHeader(Request.MessageHeader.RequestVoteMsg)
												.setPayload(MessageProto.Payload.newBuilder().setRequestVote(MessageProto.RequestVote.newBuilder()
														.setCandidateId(GlobalConfiguration.getCurrentNode().getNodeID())
														.setTerm(GlobalConfiguration.getCurrentTerm()))).build();
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
	private void waitForRAFTTimeOut()
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
