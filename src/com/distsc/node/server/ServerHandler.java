package com.distsc.node.server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import com.distsc.comm.msg.decoders.ServerMessageDecoder;
import com.distsc.comm.msg.protobuf.NodeMessageProto.Message;


public class ServerHandler extends SimpleChannelInboundHandler<Message>
{

	static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	public void channelActive(final ChannelHandlerContext ctx)
	{
		channels.add(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	{
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, Message msg)
			throws Exception 
	{
			ServerMessageDecoder.handle(msg);
			//arg0.close();
	}
	
	
}
