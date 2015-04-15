package com.distsc.comm.msg.queues.workers;
import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.AppendEntriesQueue;
import com.distsc.comm.msg.queues.AppendEntriesResultQueue;
import com.distsc.comm.msg.queues.ClientMessageMsgQueue;
import com.distsc.comm.msg.queues.NodeDiscoveryMsgQueue;
import com.distsc.comm.msg.queues.RequestQueue;
import com.distsc.comm.msg.queues.RequestVoteMsgQueue;
import com.distsc.comm.msg.queues.RequestVoteResultMsgQueue;

public class RequestWorker implements Runnable
{
	public void run()
	{
		RequestContext requestMessage = null;
		while (true)
		{
			if (RequestQueue.getCount() > 0)
			{
				requestMessage = RequestQueue.pop();
				switch (requestMessage.getRequest().getMessageHeader())
				{
				case AppendEntriesMsg:
				AppendEntriesQueue.push(requestMessage);
				break;

				case AappendEntriesResultMsg:
				AppendEntriesResultQueue.push(requestMessage);
				break;

				case RequestVoteMsg:
				RequestVoteMsgQueue.push(requestMessage);
				break;

				case RequestVoteResultMsg:
				RequestVoteResultMsgQueue.push(requestMessage);
				break;

				case NodeDiscoveryMsg:
				NodeDiscoveryMsgQueue.push(requestMessage);
				break;

				case ClientMessageMsg:
				ClientMessageMsgQueue.push(requestMessage);
				break;
					
				default:
					System.out.println("Unknown Request Message Rejected");
					break;

				}
			}
		}
	}

}
