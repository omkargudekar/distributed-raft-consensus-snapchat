package com.distsc.intercluster.server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import com.distsc.intercluster.message.ClusterMessageProto.ClusterMessage;
import com.distsc.intercluster.queues.inbound.ClusterMessageQueue;


public class InterClusterServerHandler extends SimpleChannelInboundHandler<ClusterMessage>
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
	protected void channelRead0(ChannelHandlerContext arg0, ClusterMessage msg) throws Exception 
	{
				ClusterMessageQueue.pushMessage(msg);
	} 
	
}
