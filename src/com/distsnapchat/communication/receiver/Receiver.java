package com.distsnapchat.communication.receiver;

import com.dissnapchat.raft.RAFTStatus;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * Simple SSL chat server modified from {@link TelnetServer}.
 */
public final class Receiver implements Runnable
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
             .childHandler(new ReceiverInitializer());

            b.bind(RAFTStatus.getCurrentNode().getNodePort()).sync().channel().closeFuture().sync();
        } 
        catch(Exception e)
        {
        	System.out.println(e);
        }
        finally 
        {
        	
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
