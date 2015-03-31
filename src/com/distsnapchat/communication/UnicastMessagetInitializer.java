package com.distsnapchat.communication;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import org.dissnapchat.protobuf.MessageProto;
import org.dissnapchat.protobuf.MessageProto.Message;

public class UnicastMessagetInitializer extends ChannelInitializer<SocketChannel> {

 
    public UnicastMessagetInitializer() {
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

  
        pipeline.addLast ("frameDecoder", new ProtobufVarint32FrameDecoder ());
        pipeline.addLast ("protobufDecoder", new ProtobufDecoder(Message.getDefaultInstance()));

        pipeline.addLast ("frameEncoder", new ProtobufVarint32LengthFieldPrepender ());
        pipeline.addLast ("protobufEncoder", new ProtobufEncoder ());

        pipeline.addLast(new UnicastMessageHandler());
    }
}
