package com.distsc.comm.msg.queues.workers;
import com.distsc.beans.RequestContext;
import com.distsc.client.msg.handler.ClientMessageDecoder;
import com.distsc.comm.msg.queues.ClientMessageMsgQueue;

public class ClientMessageQueueWorker implements Runnable
{
	public void run()
	{
		System.out.println("ClientMessageMsgQueueWorker Thread Started");
		RequestContext requestMessage = null;
		while (true)
		{
			if (ClientMessageMsgQueue.getCount() > 0)
			{
				System.out.println("Client Message Found...");

				requestMessage = ClientMessageMsgQueue.pop();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
