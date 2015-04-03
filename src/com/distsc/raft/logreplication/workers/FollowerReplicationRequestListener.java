package com.distsc.raft.logreplication.workers;

import com.distsc.app.GlobalConfiguration;
import com.distsc.beans.Packet;
import com.distsc.comm.msg.queues.inbound.LogReplicationRequestBuffer;
import com.distsc.comm.msg.queues.outbound.OutboundQueue;
import com.distsc.comm.protobuf.NodeMessageProto;
import com.distsc.comm.protobuf.NodeMessageProto.Message;
import com.distsc.comm.protobuf.NodeMessageProto.Message.MessageType;
import com.distsc.raft.RAFTStatus;
import com.distsc.util.ImageWriter;

public class FollowerReplicationRequestListener implements Runnable
{
	
	public void run()
	{
		System.out.println("FollowerReplicationRequestThread Started");
		while (true)
		{

			switch (RAFTStatus.getCurrentNodeState())
			{
			case Leader:
				break;

			case Candidate:
				break;

			case OrphanFollower:
				break;

			case Follower:
				replicate();
				break;

			default:
				break;
			}
		}
	}
	
	public void replicate()
	{
		if(LogReplicationRequestBuffer.getMessageCount()>0)
		{
			while(LogReplicationRequestBuffer.getMessageCount()>0)
			{
				Message clientMessage=LogReplicationRequestBuffer.popMessage();
				ImageWriter imgWriter=new ImageWriter();
				imgWriter.storeImage(clientMessage);
				
				Message msg = NodeMessageProto.Message.newBuilder().setMessageType(MessageType.LOG_REPLICATION_COMPLETE_NOTIFICATION).setNodeId(GlobalConfiguration.getCurrentNode().getNodeID()).setNodeIp(GlobalConfiguration.getCurrentNode().getNodeIP()).setNodePort(GlobalConfiguration.getCurrentNode().getNodePort()).build();
				
				Packet packet=new Packet();
				packet.setNode(RAFTStatus.getDeclaredLeader());
				packet.setMsg(msg);
				OutboundQueue.pushMessage(packet);
				
				
				
			}
			
		}
	}

}