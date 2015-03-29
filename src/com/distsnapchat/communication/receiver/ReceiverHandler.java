package com.distsnapchat.communication.receiver;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


public class ReceiverHandler extends SimpleChannelInboundHandler<String>
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
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
	{
		System.out.println("Raw Message : ["+msg+"]");
	

	}
	
	
}
