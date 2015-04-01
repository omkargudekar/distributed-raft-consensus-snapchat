package com.distributedsnapchat.raft.election.workers;

import com.distributedsnapchat.communication.MulticastMessage;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message.MessageType;
import com.distributedsnapchat.raft.RAFTStatus;

public class ElectionParticipationThread implements Runnable
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
			
			MulticastMessage multicast = new MulticastMessage();
			Message msg = NodeMessageProto.Message.newBuilder().setMessageType(MessageType.NOMINATION).setNodeId(RAFTStatus.getCurrentNode().getNodeID()).setNodeIp(RAFTStatus.getCurrentNode().getNodeIP()).setNodePort(RAFTStatus.getCurrentNode().getNodePort()).build();
			multicast.send(msg);
//			multicast.send(RAFTStatus.getNodes(), "Candidate-" + RAFTStatus.getCurrentNode().getNodeID() + "-" + RAFTStatus.getCurrentNode().getNodeIP() + "-" + RAFTStatus.getCurrentNode().getNodePort() + "\r\n");
		}
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
