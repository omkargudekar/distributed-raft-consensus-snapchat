package com.distsc.node.server;

import com.distsc.app.config.GlobalConfiguration;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public final class Server implements Runnable
{
    public void run()
    {
    	System.out.println("Receiver Thread Started...");
    	EventLoopGroup bossGroup=null;
    	EventLoopGroup workerGroup=null;
        try 
        {
        	System.out.println("Starting Server on : "+GlobalConfiguration.getCurrentNode().getNodePort());
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ServerrInitializer());
            b.bind(GlobalConfiguration.getCurrentNode().getNodePort()).sync().channel().closeFuture().sync();
           
            
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
