package com.distsc.chat.msg.handler;


import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto.Request;

public interface ClientMsgHandler
{
	public void handle(ChannelHandlerContext ctx,Request request);

}
