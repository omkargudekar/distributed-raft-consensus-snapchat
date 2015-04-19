package com.distsc.comm.msg.queues.workers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.beans.RequestContext;
import com.distsc.client.msg.handler.ClientMessageDecoder;
import com.distsc.comm.msg.queues.ClientMsgQueue;

public class ClientMessageQueueWorker implements Runnable
{
	static Logger logger = LoggerFactory.getLogger(ClientMessageQueueWorker.class);

	public void run()
	{
		logger.info("ClientMessageMsgQueueWorker Thread Started");
		RequestContext requestMessage = null;
		while (true)
		{
			if (ClientMsgQueue.getCount() > 0)
			{
				logger.info("Client Message Found");
				requestMessage = ClientMsgQueue.pop();
				handleMessage(requestMessage);
			}
			else
			{
				pause();
			}
		}
	}
	
	public void handleMessage(RequestContext request)
	{

		ClientMessageDecoder.handle(request.getContext(),request.getRequest());

		
	}
	public void pause(){
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
