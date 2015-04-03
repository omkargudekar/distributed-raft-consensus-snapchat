package com.distsc.raft.election.workers;

import com.distsc.app.GlobalConfiguration;
import com.distsc.beans.Node;
import com.distsc.beans.Packet;
import com.distsc.comm.msg.queues.inbound.NominationsBuffer;
import com.distsc.comm.msg.queues.outbound.OutboundQueue;
import com.distsc.comm.protobuf.NodeMessageProto;
import com.distsc.comm.protobuf.NodeMessageProto.Message;
import com.distsc.comm.protobuf.NodeMessageProto.Message.MessageType;
import com.distsc.raft.RAFTStatus;

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
			
			Packet packet=new Packet();
			packet.setNode(candidate);
			packet.setMsg(msg);
			OutboundQueue.pushMessage(packet);
			NominationsBuffer.reset();

		}
		else if(NominationsBuffer.getNodeCount() > 0 &&  RAFTStatus.isVoted()==true)
		{
			NominationsBuffer.reset();
		}

	}
	
	

}
