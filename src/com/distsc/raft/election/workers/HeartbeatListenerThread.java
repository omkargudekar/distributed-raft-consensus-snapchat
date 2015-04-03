package com.distsc.raft.election.workers;

import com.distsc.comm.msg.queues.inbound.HeartbeatBuffer;
import com.distsc.raft.RAFTStatus;

public class HeartbeatListenerThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("HeartbeatListenerThread Thread Started");

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
				pause();
				break;

			case Follower:
				checkHeartbeat();
				break;

			default:
				pause();
				break;
			}
		
		}

	}
	
	public void checkHeartbeat()
	{
		starRAFTTimer();
		if (HeartbeatBuffer.popNode() == null)
		{
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.OrphanFollower);
		}
		else
		{
			HeartbeatBuffer.reset();
		}
	}
	
	public void starRAFTTimer()
	{
		try
		{
			Thread.sleep(RAFTStatus.getRaftTimer());
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