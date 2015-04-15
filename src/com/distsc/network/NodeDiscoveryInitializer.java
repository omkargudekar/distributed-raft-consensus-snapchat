package com.distsc.network;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import com.distsc.comm.protobuf.MessageProto.Request;

public class NodeDiscoveryInitializer extends ChannelInitializer<SocketChannel> {

 
    public NodeDiscoveryInitializer() {
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

  
        pipeline.addLast ("frameDecoder", new ProtobufVarint32FrameDecoder ());
        pipeline.addLast ("protobufDecoder", new ProtobufDecoder(Request.getDefaultInstance()));

        pipeline.addLast ("frameEncoder", new ProtobufVarint32LengthFieldPrepender ());
        pipeline.addLast ("protobufEncoder", new ProtobufEncoder ());

        pipeline.addLast(new NodeDiscoveryHandler());
    }
}
