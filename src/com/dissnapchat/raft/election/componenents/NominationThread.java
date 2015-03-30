package com.dissnapchat.raft.election.componenents;

import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.communication.MulticastMessage;

public class NominationThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("NominationThread Started");
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
				nominate();
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

	private void nominate()
	{
		waitForRAFTTimeOut();
		if(RAFTStatus.getCurrentNodeState()==RAFTStatus.NodeState.OrphanFollower)
		{
			RAFTStatus.setVoted(true);
			System.out.println("Declaring Candidacy...");
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Candidate);
			MulticastMessage multicast = new MulticastMessage();
			multicast.send(RAFTStatus.getNodes(), "Candidate-" + RAFTStatus.getCurrentNode().getNodeID() + "-" + RAFTStatus.getCurrentNode().getNodeIP() + "-" + RAFTStatus.getCurrentNode().getNodePort() + "\r\n");
		}
	}
	
	private void waitForRAFTTimeOut()
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
