package com.distributedsnapchat.raft.logreplication.workers;

import com.distributedsnapchat.app.GlobalConfiguration;
import com.distributedsnapchat.beans.Packet;
import com.distributedsnapchat.communication.UnicastMessage;
import com.distributedsnapchat.communication.buffers.LogReplicationRequestBuffer;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message.MessageType;
import com.distributedsnapchat.raft.RAFTStatus;
import com.distributedsnapchat.util.ImageWriter;

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
				
				UnicastMessage unicastMsg=new UnicastMessage();
				Packet packet=new Packet();
				packet.setNode(RAFTStatus.getDeclaredLeader());
				packet.setMsg(msg);
				unicastMsg.pushPacket(packet);
				
				new Thread(unicastMsg).start();
				
				
				
			}
			
		}
	}

}
