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
				System.out.println("Received Message : "+requestMessage.getRequest().getMessageHeader());
				requestMessage = RequestQueue.pop();
				switch (requestMessage.getRequest().getMessageHeader())
				{
				case AppendEntriesMsg:
				System.out.println("Message pushed to AppendEntriesQueue");
				AppendEntriesQueue.push(requestMessage);
				break;

				case AappendEntriesResultMsg:
				System.out.println("Message pushed to AppendEntriesResultQueue");
				AppendEntriesResultQueue.push(requestMessage);
				break;

				case RequestVoteMsg:
				System.out.println("Message pushed to RequestVoteMsgQueue");
				RequestVoteMsgQueue.push(requestMessage);
				break;

				case RequestVoteResultMsg:
				System.out.println("Message pushed to RequestVoteResultMsgQueue");
				RequestVoteResultMsgQueue.push(requestMessage);
				break;

				case NodeDiscoveryMsg:
				System.out.println("Message pushed to NodeDiscoveryMsgQueue");
				NodeDiscoveryMsgQueue.push(requestMessage);
				break;

				case ClientMessageMsg:
				System.out.println("Message pushed to ClientMessageMsgQueue");
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
