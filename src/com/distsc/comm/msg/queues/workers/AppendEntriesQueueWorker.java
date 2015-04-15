package com.distsc.comm.msg.queues.workers;
import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.inbound.AppendEntriesQueue;
import com.distsc.raft.RAFTStatus;

public class AppendEntriesQueueWorker implements Runnable
{
	public void run()
	{
		RequestContext requestMessage = null;
		while (true)
		{
			if (AppendEntriesQueue.getCount() > 0)
			{
				requestMessage = AppendEntriesQueue.pop();
				switch (RAFTStatus.getCurrentNodeState())
				{
				case Leader:
					break;

				case Candidate:
					break;

				case Follower:
					break;
					
				default:
					break;
				}
			}
		}
	}

}
