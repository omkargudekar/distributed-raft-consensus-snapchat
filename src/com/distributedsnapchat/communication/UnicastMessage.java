package com.distributedsnapchat.communication;

import java.util.Stack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.distributedsnapchat.beans.Node;
import com.distributedsnapchat.beans.Packet;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;

public class UnicastMessage implements Runnable
{
	
	Stack<Packet> packetStack=new Stack<Packet>();
	
	public void pushPacket(Packet packet)
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
			group = new NioEventLoopGroup(1);
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new UnicastMessagetInitializer());
			
			while(packetStack.empty()==false)
			{
				packet=packetStack.pop();
				node=packet.getNode();
				msg=packet.getMsg();
				ch = b.connect(node.getNodeIP(), node.getNodePort()).sync().channel();
				System.out.println(ch);
				lastWriteFuture = ch.writeAndFlush(msg);
				lastWriteFuture.channel().close();
				System.out.println("Sending Message to node : "+node);
			}
	
		

		}
		catch (Exception e)
		{
			//System.out.println(e);
		}
		finally
		{
			System.out.println("Message Sent : Connection closed");
			try
			{
				group.shutdown();
			}
			catch(Exception e)
			{
				
			}
		}

	}

}
