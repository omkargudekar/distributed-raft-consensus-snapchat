package com.distsc.chat.msg.handler;


import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.ClientMessageProto.ClientMsg;

public interface ClientMsgHandler
{
	public void handle(ChannelHandlerContext ctx,ClientMsg msg);

}
