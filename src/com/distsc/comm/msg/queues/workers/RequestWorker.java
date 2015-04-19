package com.distsc.comm.msg.queues.workers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.AppendEntriesQueue;
import com.distsc.comm.msg.queues.AppendEntriesResultQueue;
import com.distsc.comm.msg.queues.ClientMsgQueue;
import com.distsc.comm.msg.queues.NodeDiscoveryMsgQueue;
import com.distsc.comm.msg.queues.RequestQueue;
import com.distsc.comm.msg.queues.RequestVoteMsgQueue;
import com.distsc.comm.msg.queues.RequestVoteResultMsgQueue;

public class RequestWorker implements Runnable
{
	static Logger logger = LoggerFactory.getLogger(RequestWorker.class);

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
				logger.info("Message pushed to AppendEntriesQueue");
				AppendEntriesQueue.push(requestMessage);
				break;

				case AappendEntriesResultMsg:
				logger.info("Message pushed to AppendEntriesResultQueue");
				AppendEntriesResultQueue.push(requestMessage);
				break;

				case RequestVoteMsg:
				logger.info("Message pushed to RequestVoteMsgQueue");
				RequestVoteMsgQueue.push(requestMessage);
				break;

				case RequestVoteResultMsg:
				logger.info("Message pushed to RequestVoteResultMsgQueue");
				RequestVoteResultMsgQueue.push(requestMessage);
				break;

				case NodeDiscoveryMsg:
				logger.info("Message pushed to NodeDiscoveryMsg ");
				NodeDiscoveryMsgQueue.push(requestMessage);
				break;

				case ClientMessageMsg:
				logger.info("Message pushed to ClientMessageMsgQueue");
				ClientMsgQueue.push(requestMessage);
				break;
					
				default:
				logger.error("Unknown Request Message Rejected");
				break;

				}
			}
			else
			{
				pause();
			}
		}
	}
	public void pause()
	{
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			logger.error(e.toString());
			e.printStackTrace();
		}
	}

}
