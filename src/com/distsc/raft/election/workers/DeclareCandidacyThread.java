package com.distsc.raft.election.workers;

import com.distsc.app.GlobalConfiguration;
import com.distsc.comm.msg.queues.inbound.HeartbeatQueue;
import com.distsc.comm.msg.queues.inbound.NominationsQueue;
import com.distsc.comm.msg.queues.inbound.VotesQueue;
import com.distsc.comm.outbound.OutboundMulticast;
import com.distsc.comm.protobuf.NodeMessageProto;
import com.distsc.comm.protobuf.NodeMessageProto.Message;
import com.distsc.comm.protobuf.NodeMessageProto.Message.MessageType;
import com.distsc.raft.RAFTStatus;

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

			case OrphanFollower:
				nominate();
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

	private void nominate()
	{
		waitForRAFTTimeOut();
		if(RAFTStatus.getCurrentNodeState()==RAFTStatus.NodeState.OrphanFollower)
		{
			RAFTStatus.setVoted(true);
			System.out.println("Declaring Candidacy...");
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Candidate);
			
			OutboundMulticast multicast = new OutboundMulticast();
			Message msg = NodeMessageProto.Message.newBuilder().setMessageType(MessageType.NOMINATION).setNodeId(GlobalConfiguration.getCurrentNode().getNodeID()).setNodeIp(GlobalConfiguration.getCurrentNode().getNodeIP()).setNodePort(GlobalConfiguration.getCurrentNode().getNodePort()).build();
			multicast.send(msg);
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
		RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.OrphanFollower);
		VotesQueue.reset();
		NominationsQueue.reset();
		HeartbeatQueue.reset();
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
