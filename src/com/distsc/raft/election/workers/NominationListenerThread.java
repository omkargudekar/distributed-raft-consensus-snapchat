package com.distsc.raft.election.workers;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.beans.Node;
import com.distsc.beans.Packet;
import com.distsc.comm.msg.queues.inbound.NominationsQueue;
import com.distsc.comm.msg.queues.outbound.MgmtMsgOutboundQueue;
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
		if (NominationsQueue.getNodeCount() > 0 &&  RAFTStatus.isVoted()==false && RAFTStatus.getDeclaredLeader()==null)
		{
			Node candidate = NominationsQueue.popCandidate();
			RAFTStatus.setVoted(true);
			System.out.println("Voting for candidate : " + candidate);
			Message msg = NodeMessageProto.Message.newBuilder().setMessageType(MessageType.VOTE).setNodeId(GlobalConfiguration.getCurrentNode().getNodeID()).setNodeIp(GlobalConfiguration.getCurrentNode().getNodeIP()).setNodePort(GlobalConfiguration.getCurrentNode().getNodePort()).build();
			
			Packet packet=new Packet();
			packet.setNode(candidate);
			packet.setMsg(msg);
			MgmtMsgOutboundQueue.pushMessage(packet);
			NominationsQueue.reset();

		}
		else if(NominationsQueue.getNodeCount() > 0 &&  RAFTStatus.isVoted()==true)
		{
			NominationsQueue.reset();
		}

	}
	
	

}
