package com.distsc.comm.msg.queues.workers;
import com.distsc.beans.RequestContext;
import com.distsc.client.msg.handler.ClientMessageDecoder;
import com.distsc.comm.msg.queues.AppendEntriesQueue;

public class ClientMessageMsgQueueWorker implements Runnable
{
	public void run()
	{
		RequestContext requestMessage = null;
		while (true)
		{
			if (AppendEntriesQueue.getCount() > 0)
			{
				requestMessage = AppendEntriesQueue.pop();
				handleMessage(requestMessage);
			}
		}
	}
	
	public void handleMessage(RequestContext request)
	{
		
		ClientMessageDecoder.handle(request.getContext(),request.getRequest());

		
	}

}
