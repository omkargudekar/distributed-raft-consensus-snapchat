package com.distsc.comm.msg.queues.workers;
import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.inbound.AppendEntriesQueue;
import com.distsc.comm.msg.queues.inbound.AppendEntriesResultQueue;
import com.distsc.comm.msg.queues.inbound.ClientMessageMsgQueue;
import com.distsc.comm.msg.queues.inbound.NodeDiscoveryMsgQueue;
import com.distsc.comm.msg.queues.inbound.RequestQueue;
import com.distsc.comm.msg.queues.inbound.RequestVoteMsgQueue;
import com.distsc.comm.msg.queues.inbound.RequestVoteResultMsgQueue;

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
