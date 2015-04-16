package com.distc.cluster.server.listener;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.distc.cluster.msg.queue.ClusterRequestContext;
import com.distc.cluster.msg.queue.ClusterRequestQueue;
import com.distc.cluster.proto.App.Request;

public class ClusterListenerHandler extends SimpleChannelInboundHandler<Request> 
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
		if(msg.getBody().getClientMessage()==null)
		{
			ClusterRequestQueue.push(new ClusterRequestContext(ctx,msg));
		}
		else
		{
			System.out.println();
		}
		
		
	}
}
