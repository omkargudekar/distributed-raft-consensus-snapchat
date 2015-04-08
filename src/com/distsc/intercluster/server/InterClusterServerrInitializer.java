package com.distsc.intercluster.server;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import com.distsc.intercluster.message.ClusterMessageProto.ClusterMessage;
public class InterClusterServerrInitializer extends ChannelInitializer<SocketChannel> {


    public InterClusterServerrInitializer() {
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast ("frameDecoder", new ProtobufVarint32FrameDecoder ());
        pipeline.addLast ("protobufDecoder", new ProtobufDecoder(ClusterMessage.getDefaultInstance()));
        pipeline.addLast ("frameEncoder", new ProtobufVarint32LengthFieldPrepender ());
        pipeline.addLast ("protobufEncoder", new ProtobufEncoder ());
        pipeline.addLast(new InterClusterServerHandler());
    }
}