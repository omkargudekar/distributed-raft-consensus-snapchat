package com.distsc.chat.worker;

import com.distsc.chat.server.ClientContext;
import com.distsc.comm.msg.queues.outbound.OuboundClientMessageQueue;
import com.distsc.comm.protobuf.ClientMessage.ClientMsg;

public class ClientServerOutboundWorker implements Runnable
{

	
	@Override
	public void run()
	{
		try
		{
			ClientMsg msg=null;
			if(OuboundClientMessageQueue.getMessageCount()>0)
			{
				msg=OuboundClientMessageQueue.popMessage();
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
