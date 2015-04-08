package com.distsc.intercluster.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.distsc.intercluster.msg.protobuff.ClusterMessageProto.ClusterMessage;

public class InterClusterOutboundConnectionHandler extends SimpleChannelInboundHandler<ClusterMessage> 
{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ClusterMessage msg)
			throws Exception 
	{
	
			
	}
}
