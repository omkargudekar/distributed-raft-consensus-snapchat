package com.distributedsnapchat.communication.nodes.receiver;

import com.distributedsnapchat.app.GlobalConfiguration;
import com.distributedsnapchat.raft.RAFTStatus;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * Simple SSL chat server modified from {@link TelnetServer}.
 */
public final class NodeReceiver implements Runnable
{
    public void run()
    {
    	System.out.println("Receiver Thread Started...");
    	EventLoopGroup bossGroup=null;
    	EventLoopGroup workerGroup=null;
        try {
          
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new NodeReceiverInitializer());

            b.bind(GlobalConfiguration.getCurrentNode().getNodePort()).sync().channel().closeFuture().sync();
        } 
        catch(Exception e)
        {
        	
        }
        finally 
        {
        	
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
