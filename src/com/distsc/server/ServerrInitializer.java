package com.distsc.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import com.distsc.intercluster.proto.App;
public class ServerrInitializer extends ChannelInitializer<SocketChannel> {

	static Logger logger = LoggerFactory.getLogger(ServerrInitializer.class);


    public ServerrInitializer() {
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
    	pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(67108864, 0, 4, 0, 4));
    	pipeline.addLast("protobufDecoder", new ProtobufDecoder(App.Request.getDefaultInstance()));
    	pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
    	pipeline.addLast("protobufEncoder", new ProtobufEncoder());
        pipeline.addLast(new ServerHandler());
    }
}
