package com.distsc.network;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NodeDiscoveryWorker implements Runnable
{
	private EventLoopGroup group = null;
	private ChannelFuture lastWriteFuture = null;
	private Channel ch=null;
	public void run()
	{
		try
		{
			group = new NioEventLoopGroup(1);
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new NodeDiscoveryInitializer());
			
			try
			{		
				for(Node node : GlobalConfiguration.getNodes())
				{
					if(NetowrkMap.)
					ch = b.connect(node.getNodeIP(), node.getNodePort()).sync().channel();
					lastWriteFuture = ch.writeAndFlush(msg);
					lastWriteFuture.channel().close().sync();	
				}

			}
			catch(Exception e)
			{
						System.out.println(e);
			}
			
		

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				group.shutdownGracefully();
			}
			catch(Exception e)
			{
				
			}
		}

	}
	
	public void init()
	{
		
		
	}
	
		
	
}
