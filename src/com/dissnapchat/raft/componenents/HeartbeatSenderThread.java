package com.dissnapchat.raft.componenents;

import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.communication.MulticastMessage;

public class HeartbeatSenderThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("heartBeatSender Thread Started");

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
		multicast.send(RAFTStatus.getNodes(), "Heartbeat-"+RAFTStatus.getCurrentNode().getNodeID()+"-"+RAFTStatus.getCurrentNode().getNodeIP()+"-"+RAFTStatus.getCurrentNode().getNodePort()+"\r\n");
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
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}

}
