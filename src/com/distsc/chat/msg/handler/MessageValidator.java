package com.distsc.chat.msg.handler;

import com.distsc.app.GlobalConfiguration;
import com.distsc.chat.server.ClientContext;
import com.distsc.comm.protobuf.ClientMessage.ClientMsg;

public class MessageValidator
{
	
	
	public boolean validateRequest(ClientMsg msg)
	{
		
		if(msg.getSerializedSize() <= GlobalConfiguration.getMessageLimit())
		{
			return true;
		}
		
		return false;
	}
	
	
	public boolean validateMessageSize(ClientMsg msg)
	{
		
		if(msg.getSerializedSize() <= GlobalConfiguration.getMessageLimit())
		{
			return true;
		}
		
		return false;
	}
	public boolean validateCheckSum(ClientMsg msg)
	{
		if
		
	}
	
	public boolean checkReceiver(ClientMsg msg)
	{
		if(ClientContext.isExist(msg.getReceiverUserName()))
		{
			return true;
		}
		return false;
	}

}
