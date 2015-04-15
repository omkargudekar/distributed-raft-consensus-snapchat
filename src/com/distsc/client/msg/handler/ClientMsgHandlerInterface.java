package com.distsc.client.msg.handler;


import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto.Request;

public interface ClientMsgHandlerInterface
{
	public void handle(ChannelHandlerContext ctx,Request request);

}
