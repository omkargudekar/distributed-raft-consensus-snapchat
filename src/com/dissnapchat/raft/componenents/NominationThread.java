package com.dissnapchat.raft.componenents;

import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.communication.MulticastMessage;

public class NominationThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("DeclareCandidacy Thread Started");
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
				declare();
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

	private void declare()
	{
		RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Candidate);
		MulticastMessage multicast = new MulticastMessage();
		multicast.send(RAFTStatus.getNodes(), "Candidate-" + RAFTStatus.getCurrentNode().getNodeID() + "-" + RAFTStatus.getCurrentNode().getNodeIP() + "-" + RAFTStatus.getCurrentNode().getNodePort() + "\r\n");
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
