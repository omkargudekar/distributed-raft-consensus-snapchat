package com.distsc.server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.RequestQueue;
import com.distsc.comm.protobuf.MessageProto.Request;


public class ServerHandler extends SimpleChannelInboundHandler<Request>
{

	static Logger logger = LoggerFactory.getLogger(ServerHandler.class);

	static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	public void channelActive(final ChannelHandlerContext ctx)
	{
		logger.info("Channel Added "+ctx);
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
			logger.info("Received Request :  "+msg);
			RequestQueue.push(new RequestContext(ctx,msg));
	}
	
	
}
