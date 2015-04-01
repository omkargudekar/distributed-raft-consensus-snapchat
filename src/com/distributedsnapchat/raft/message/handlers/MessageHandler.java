package com.distributedsnapchat.raft.message.handlers;

import com.distributedsnapchat.communication.buffers.ReceivedMessageBuffer;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;

public class MessageHandler implements Handler
{

	@Override
	public void handle(Message msg)
	{
		System.out.println("Inside Message Handelr ");
		ReceivedMessageBuffer.pushMessage(msg);		
	}

}
