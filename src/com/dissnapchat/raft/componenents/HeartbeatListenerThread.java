package com.dissnapchat.raft.componenents;

import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.communication.buffers.HeartbeatBuffer;

public class HeartbeatListenerThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("HeartbeatMonitor Thread Started");

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
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}

}
