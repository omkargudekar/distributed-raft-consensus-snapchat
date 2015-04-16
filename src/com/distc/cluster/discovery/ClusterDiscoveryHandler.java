package com.distc.cluster.discovery;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.distc.cluster.msg.queue.ClusterDiscoveryQueue;
import com.distc.cluster.proto.App.Request;


public class ClusterDiscoveryHandler extends SimpleChannelInboundHandler<Request> 
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
		
		ClusterDiscoveryQueue.push(msg);
	}
}
