package com.distsc.raft.election.workers;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.raft.RAFTStatus;
import com.distsc.server.ClusterMulticast;

public class HeartbeatSenderThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("HeartbeatSenderThread Started");

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
		ClusterMulticast followerMulticast=new ClusterMulticast();
		
		Request msg=Request.newBuilder().setMessageHeader(Request.MessageHeader.AppendEntriesMsg)
											.setPayload(MessageProto.Payload.newBuilder().setAppendEntries(MessageProto.AppendEntries.newBuilder()
													.setLeaderId(GlobalConfiguration.getCurrentNode().getNodeID())
													.setTerm(RAFTStatus.getCurrentTerm()))).build();
		followerMulticast.send(msg);
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
