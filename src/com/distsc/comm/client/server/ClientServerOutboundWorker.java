package com.distsc.comm.client.server;

import com.distsc.comm.msg.queues.outbound.OuboundClientQueue;
import com.distsc.comm.protobuf.ClientMessage.ClientMsg;

public class ClientServerOutboundWorker implements Runnable
{

	
	@Override
	public void run()
	{
		try
		{
			ClientMsg msg=null;
			if(OuboundClientQueue.getMessageCount()>0)
			{
				msg=OuboundClientQueue.popMessage();
				ClientContext.getClientContext(msg.getReceiverUserName()).writeAndFlush(msg);
			
			}
			else
			{
				pause();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
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
			e.printStackTrace();
		}
	}
}
