package com.jachs.netty.http.server.serverbootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author zhanchaohan
 * 
 */
public class ServerBootstrapTest {
    
    public static void main ( String[] args ) throws InterruptedException {
        ServerBootstrap sb = new ServerBootstrap ();

        EventLoopGroup bossGroup = new NioEventLoopGroup ( 1 );

        sb.group ( bossGroup );
        sb.channel ( NioServerSocketChannel.class );
        sb.handler ( new LoggingHandler ( LogLevel.INFO ) );
        sb.childHandler ( new ServerBootstrapInitializer ( ) );

        
        Channel ch=sb.bind ( 8080 ).sync ().channel ();
        ch.closeFuture().sync();
        bossGroup.shutdownGracefully ();
    }
}
