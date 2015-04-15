package com.distsc.server.listener;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.inbound.RequestQueue;
import com.distsc.comm.protobuf.MessageProto.Request;

public class ListenerConnectionHandler extends SimpleChannelInboundHandler<Request> 
{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Request msg)
			throws Exception 
	{	
		RequestQueue.push(new RequestContext(ctx,msg));
	}
}
