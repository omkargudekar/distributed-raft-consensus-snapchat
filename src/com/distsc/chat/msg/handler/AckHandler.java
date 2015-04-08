package com.distsc.chat.msg.handler;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.msg.protobuf.ClientMessageProto.ClientMsg;

public class AckHandler implements ClientMsgHandler
{

	@Override
	public void handle(ChannelHandlerContext ctx,ClientMsg msg)
	{
		System.out.println(msg.getMessageType()+" From "+msg.getSenderUserName());		
	}

}
