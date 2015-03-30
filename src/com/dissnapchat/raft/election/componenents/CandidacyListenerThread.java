package com.dissnapchat.raft.election.componenents;

import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.beans.Node;
import com.distsnapchat.communication.UnicastMessage;
import com.distsnapchat.communication.buffers.NominationsBuffer;

public class CandidacyListenerThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("CandidacyListernerThread  Started");
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
		if (NominationsBuffer.getNodeCount() > 0 &&  RAFTStatus.isVoted()==false)
		{
			Node candidate = NominationsBuffer.popCandidate();
			NominationsBuffer.reset();
			RAFTStatus.setVoted(true);
			System.out.println("Voting for candidate : " + candidate);
			new Thread(new UnicastMessage(candidate.getNodeIP(), candidate.getNodePort(), "Vote-" + RAFTStatus.getCurrentNode().getNodeID() + "-" + RAFTStatus.getCurrentNode().getNodeIP() + "-" + RAFTStatus.getCurrentNode().getNodePort() + "\r\n")).start();
		}

	}

}
