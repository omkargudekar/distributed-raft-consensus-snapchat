package com.distsc.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.RequestQueue;
import com.distsc.comm.protobuf.MessageProto.Request;

public class ListenerHandler extends SimpleChannelInboundHandler<Request> 
{
	static Logger logger = LoggerFactory.getLogger(ListenerHandler.class);
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Request msg)
			throws Exception 
	{	
		logger.debug(msg.toString());
		RequestQueue.push(new RequestContext(ctx,msg));
	}
}
