package com.distributedsnapchat.raft.election.workers;

import com.distributedsnapchat.app.GlobalConfiguration;
import com.distributedsnapchat.beans.Node;
import com.distributedsnapchat.beans.Packet;
import com.distributedsnapchat.communication.UnicastMessage;
import com.distributedsnapchat.communication.buffers.NominationsBuffer;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message.MessageType;
import com.distributedsnapchat.raft.RAFTStatus;

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
			Message msg = NodeMessageProto.Message.newBuilder().setMessageType(MessageType.VOTE).setNodeId(GlobalConfiguration.getCurrentNode().getNodeID()).setNodeIp(GlobalConfiguration.getCurrentNode().getNodeIP()).setNodePort(GlobalConfiguration.getCurrentNode().getNodePort()).build();
			
			UnicastMessage unicastMsg=new UnicastMessage();
			Packet packet=new Packet();
			packet.setNode(candidate);
			packet.setMsg(msg);
			unicastMsg.pushPacket(packet);
			
			new Thread(unicastMsg).start();
			NominationsBuffer.reset();

		}
		else if(NominationsBuffer.getNodeCount() > 0 &&  RAFTStatus.isVoted()==true)
		{
			NominationsBuffer.reset();
		}

	}
	
	

}
