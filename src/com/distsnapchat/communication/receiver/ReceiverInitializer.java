package com.distsnapchat.communication.receiver;


import org.dissnapchat.protobuf.MessageProto.Message;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
public class ReceiverInitializer extends ChannelInitializer<SocketChannel> {


    public ReceiverInitializer() {
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast ("frameDecoder", new ProtobufVarint32FrameDecoder ());
        pipeline.addLast ("protobufDecoder", new ProtobufDecoder(Message.getDefaultInstance()));
        pipeline.addLast ("frameEncoder", new ProtobufVarint32LengthFieldPrepender ());
        pipeline.addLast ("protobufEncoder", new ProtobufEncoder ());
        pipeline.addLast(new ReceiverHandler());
    }
}
