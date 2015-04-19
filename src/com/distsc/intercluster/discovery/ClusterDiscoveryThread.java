package com.distsc.intercluster.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.intercluster.config.ClusterConfiguration;
import com.distsc.intercluster.map.InboundClusterContextMap;
import com.distsc.intercluster.proto.App;
import com.distsc.intercluster.proto.App.Request;
import com.distsc.intercluster.server.ClusterNode;


public class ClusterDiscoveryThread implements Runnable
{
	static Logger logger = LoggerFactory.getLogger(ClusterClientMsgProcessor.class);
	private EventLoopGroup group = null;
	private ChannelFuture lastWriteFuture = null;
	private Channel ch = null;
	public void run()
	{
		logger.info("ClusterDiscoveryThread Started...");
		try
		{
			group = new NioEventLoopGroup(1);
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new ClusterDiscoveryInitializer());
			while (true)
			{
				try
				{
					for (ClusterNode node : ClusterConfiguration.getNodes())
					{
						if (!InboundClusterContextMap.isChannelExist(node.getClusterID()))
						{
							logger.info("Trying to connect...."+node.getNodeIP());
							ch = b.connect(node.getNodeIP(), node.getNodePort()).sync().channel();
							logger.info("Connected to "+node.getNodePort());
							lastWriteFuture = ch.writeAndFlush(sendJoinMessage());
							logger.info("Sending Message to "+node.getNodeIP()+node.getNodePort());
							InboundClusterContextMap.addClusterContextChnnelContext(node.getClusterID(),ch);
						}
						
					}
					pause();

				}
				catch (Exception e)
				{
					logger.error(e.toString());
				}

			}

		}
		catch (Exception e)
		{
			logger.error(e.toString());
		}
		finally
		{
			try
			{
				lastWriteFuture.channel().close().sync();
				group.shutdownGracefully();
			}
			catch (Exception e)
			{
				logger.error(e.toString());
			}
		}

	}

	public boolean isChannelActive(ChannelHandlerContext ctx)
	{
		
		boolean writable=false;
		try
		{
			writable=true;
		}
		catch(Exception e)
		{
			writable=false;

		}
		
		return writable;
		
	}
	public void pause()
	{
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public Request sendJoinMessage()
	{
		
		Request req=null;
		try
		{
		req= App.Request.newBuilder()
						  .setJoinMessage(
								  App.JoinMessage
								  .newBuilder()
								  .setFromClusterId(Integer.parseInt(ClusterConfiguration.getCurrentClusterNode().getClusterID()))
								  .setFromNodeId(Integer.parseInt(GlobalConfiguration.getCurrentNode().getNodeID())))
						  .build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return req;
	}


}
