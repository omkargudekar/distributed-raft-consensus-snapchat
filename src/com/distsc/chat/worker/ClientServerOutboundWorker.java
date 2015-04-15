package com.distsc.chat.worker;

import com.distsc.chat.server.ChatContext;
import com.distsc.comm.msg.protobuf.ClientMessageProto.ClientMsg;
import com.distsc.comm.msg.queues.outbound.ClientMsgOutboundQueue;

public class ClientServerOutboundWorker implements Runnable
{

	
	@Override
	public void run()
	{
		try
		{
			ClientMsg msg=null;
			while(true)
			{
			if(ClientMsgOutboundQueue.getMessageCount()>0)
			{
				msg=ClientMsgOutboundQueue.popMessage();
				ChatContext.getClientContext(msg.getReceiverUserName()).writeAndFlush(msg);
			
			}
			else
			{
				pause();
			}
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
