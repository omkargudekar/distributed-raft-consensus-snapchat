package com.distsnapchat.communication;

import java.util.Stack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.dissnapchat.protobuf.MessageProto.Message;

import com.distsnapchat.beans.Node;
import com.distsnapchat.beans.Packet;

public class UnicastMessage implements Runnable
{
	
	Stack<Packet> packetStack=new Stack<Packet>();
	
	public void pusPacket(Packet packet)
	{
		packetStack.add(packet);
	}
	public void run()
	{

		
		EventLoopGroup group = null;
		ChannelFuture lastWriteFuture = null;
		Packet packet=null;
		Channel ch=null;
		Node node=null;
		Message msg=null;
		try
		{
			
			
			
			while(packetStack.empty()==false)
			{
				group = new NioEventLoopGroup();
				Bootstrap b = new Bootstrap();
				b.group(group).channel(NioSocketChannel.class).handler(new UnicastMessagetInitializer());
				packet=packetStack.pop();
				node=packet.getNode();
				msg=packet.getMsg();
				ch = b.connect(node.getNodeIP(), node.getNodePort()).sync().channel();
				lastWriteFuture = ch.writeAndFlush(msg);
				lastWriteFuture.channel().closeFuture().sync();
				group.shutdownGracefully();
			}
	
		

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			//group.shutdownGracefully();
		}

	}

}
