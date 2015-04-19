package com.distsc.raft.election.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.raft.RAFTStatus;
import com.distsc.server.ServerMulticast;

public class HeartbeatSenderThread implements Runnable
{

	static Logger logger = LoggerFactory.getLogger(HeartbeatSenderThread.class);

	@Override
	public void run()
	{
		logger.info("HeartbeatSenderThread Started");

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
		logger.debug("Sending Heatbeat");

		ServerMulticast followerMulticast=new ServerMulticast();
		
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
