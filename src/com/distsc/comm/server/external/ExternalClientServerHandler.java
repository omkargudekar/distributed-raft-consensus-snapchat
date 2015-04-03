package com.distsc.comm.server.external;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import com.distsc.comm.msg.decoders.ClientMessageDecoder;
import com.distsc.comm.protobuf.NodeMessageProto.Message;


public class ExternalClientServerHandler extends SimpleChannelInboundHandler<Message>
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
			ClientMessageDecoder.handle(msg);
	}
	
	
}
