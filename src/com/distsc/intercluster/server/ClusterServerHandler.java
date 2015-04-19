package com.distsc.intercluster.server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import com.distsc.comm.msg.queues.ClientMsgQueue;
import com.distsc.intercluster.msg.queue.ClusterRequestContext;
import com.distsc.intercluster.proto.App.Request;


public class ClusterServerHandler extends SimpleChannelInboundHandler<Request>
{

	static Logger logger = LoggerFactory.getLogger(ClusterServerHandler.class);

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
	protected void channelRead0(ChannelHandlerContext ctx, Request msg)
			throws Exception 
	{
		
		logger.info("Processing ClusterMessage");

		ClientMsgQueue.pushClusterClientRequest(new ClusterRequestContext(ctx,msg));
	}
	
	
}
