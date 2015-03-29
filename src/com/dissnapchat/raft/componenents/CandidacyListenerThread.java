package com.dissnapchat.raft.componenents;

import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.beans.Node;
import com.distsnapchat.communication.UnicastMessage;
import com.distsnapchat.communication.buffers.CandidacyBuffer;

public class CandidacyListenerThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("CandidacyMonitor Thread Started");
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
				vote();
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

	private  void vote()
	{
		if (CandidacyBuffer.getNodeCount() > 0)
		{
			Node candidate = CandidacyBuffer.popCandidate();
			CandidacyBuffer.reset();
			System.out.println("Voting for candidate : " + candidate);
			new Thread(new UnicastMessage(candidate.getNodeIP(), candidate.getNodePort(), "Vote-" + RAFTStatus.getCurrentNode().getNodeID() + "-" + RAFTStatus.getCurrentNode().getNodeIP() + "-" + RAFTStatus.getCurrentNode().getNodePort() + "\r\n")).start();
		}

	}

}
