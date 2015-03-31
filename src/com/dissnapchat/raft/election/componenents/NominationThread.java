package com.dissnapchat.raft.election.componenents;

import org.dissnapchat.protobuf.MessageProto;
import org.dissnapchat.protobuf.MessageProto.Message;
import org.dissnapchat.protobuf.MessageProto.Message.MessageType;

import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.communication.MulticastMessage;

public class NominationThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("NominationThread Started");
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
			Message msg = MessageProto.Message.newBuilder().setMessageType(MessageType.NOMINATION).setNodeId(RAFTStatus.getCurrentNode().getNodeID()).setNodeIp(RAFTStatus.getCurrentNode().getNodeIP()).setNodePort(RAFTStatus.getCurrentNode().getNodePort()).build();
//			multicast.send(RAFTStatus.getNodes(), "Candidate-" + RAFTStatus.getCurrentNode().getNodeID() + "-" + RAFTStatus.getCurrentNode().getNodeIP() + "-" + RAFTStatus.getCurrentNode().getNodePort() + "\r\n");
			multicast.send(msg);
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
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}

}
