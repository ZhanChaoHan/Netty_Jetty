package com.jachs.netty.http.server.serverbootstrap;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;

/**
 * @author zhanchaohan
 * 
 */
public class ServerBootstrapInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel ( SocketChannel ch ) throws Exception {
        CorsConfig corsConfig = CorsConfigBuilder.forAnyOrigin ().allowNullOrigin ().allowCredentials ().build ();

        ChannelPipeline pipeline = ch.pipeline ();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpServerExpectContinueHandler());
        pipeline.addLast ( new ServerBootstrapHandler () );
        pipeline.addLast ( new CorsHandler(corsConfig) );
    }

}
