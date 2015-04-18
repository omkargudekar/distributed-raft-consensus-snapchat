package com.distc.cluster.server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import com.distc.cluster.proto.App.Request;


public class ClusterServerHandler extends SimpleChannelInboundHandler<Request>
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
	protected void channelRead0(ChannelHandlerContext ctx, Request msg)
			throws Exception 
	{
		
		System.out.println("****************************Cluster Message Received");
		//ClusterRequestQueue.push(new ClusterRequestContext(ctx,msg));
	}
	
	
}
