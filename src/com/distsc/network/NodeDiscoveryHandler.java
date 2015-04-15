package com.distsc.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.distsc.comm.protobuf.MessageProto.Request;

public class NodeDiscoveryHandler extends SimpleChannelInboundHandler<Request> 
{
	NodeDiscoveryWorker nodeMessageDecoder=new NodeDiscoveryWorker();
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Request msg)
			throws Exception 
	{
		
		NetworkDiscoveryQueue.push(msg);
	}
}
