package com.distsc.network.maps.discovery;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.distsc.comm.msg.queues.NodeDiscoveryQueue;
import com.distsc.comm.protobuf.MessageProto.Request;

public class NodeDiscoveryHandler extends SimpleChannelInboundHandler<Request> 
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
		
		NodeDiscoveryQueue.push(msg);
	}
}
