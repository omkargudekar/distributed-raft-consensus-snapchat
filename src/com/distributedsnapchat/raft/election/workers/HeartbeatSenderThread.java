package com.distributedsnapchat.raft.election.workers;

import com.distributedsnapchat.communication.MulticastMessage;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message.MessageType;
import com.distributedsnapchat.raft.RAFTStatus;

public class HeartbeatSenderThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("heartbeatSenderThread Started");

		while (true)
		{
			
			switch (RAFTStatus.getCurrentNodeState())
			{
			case Leader:
				sendHeartbeat();
				break;

			case Candidate:
				pause();
				break;

			case OrphanFollower:
				pause();
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
	
	private void sendHeartbeat()
	{

		MulticastMessage multicast = new MulticastMessage();
		Message msg = NodeMessageProto.Message.newBuilder().setMessageType(MessageType.HEARTBEAT).setNodeId(RAFTStatus.getCurrentNode().getNodeID()).setNodeIp(RAFTStatus.getCurrentNode().getNodeIP()).setNodePort(RAFTStatus.getCurrentNode().getNodePort()).build();
		multicast.send(msg);
//		multicast.send(RAFTStatus.getNodes(), "Heartbeat-"+RAFTStatus.getCurrentNode().getNodeID()+"-"+RAFTStatus.getCurrentNode().getNodeIP()+"-"+RAFTStatus.getCurrentNode().getNodePort()+"\r\n");
		nextHeartbeatWait();
	}
	
	private void nextHeartbeatWait()
	{
		try
		{
			Thread.sleep(RAFTStatus.getHeartBeatFrequency());
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
