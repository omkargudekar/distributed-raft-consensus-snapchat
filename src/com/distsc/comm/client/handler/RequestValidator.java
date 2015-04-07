package com.distsc.comm.client.handler;

import com.distsc.app.GlobalConfiguration;
import com.distsc.comm.protobuf.ClientMessage.ClientMsg;

public class RequestValidator
{
	
	public boolean validateRequest(ClientMsg msg)
	{
		
		if(msg.getSerializedSize() <= GlobalConfiguration.getMessageLimit())
		{
			return true;
		}
		
		return false;
	}

}
