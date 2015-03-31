package com.dissnapchat.raft.election.componenents;

import org.dissnapchat.protobuf.MessageProto;
import org.dissnapchat.protobuf.MessageProto.Message;
import org.dissnapchat.protobuf.MessageProto.Message.MessageType;

import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.beans.Node;
import com.distsnapchat.communication.UnicastMessage;
import com.distsnapchat.communication.buffers.NominationsBuffer;

public class NominationListenerThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("NominationListernerThread  Started");
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
				vote();
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
		if (NominationsBuffer.getNodeCount() > 0 &&  RAFTStatus.isVoted()==false)
		{
			Node candidate = NominationsBuffer.popCandidate();
			RAFTStatus.setVoted(true);
			System.out.println("Voting for candidate : " + candidate);
			Message msg = MessageProto.Message.newBuilder().setMessageType(MessageType.VOTE).setNodeId(RAFTStatus.getCurrentNode().getNodeID()).setNodeIp(RAFTStatus.getCurrentNode().getNodeIP()).setNodePort(RAFTStatus.getCurrentNode().getNodePort()).build();
			new Thread(new UnicastMessage(candidate.getNodeIP(), candidate.getNodePort(), msg)).start();
			NominationsBuffer.reset();

		}
		else if(NominationsBuffer.getNodeCount() > 0 &&  RAFTStatus.isVoted()==true)
		{
			NominationsBuffer.reset();
		}

	}
	
	

}
