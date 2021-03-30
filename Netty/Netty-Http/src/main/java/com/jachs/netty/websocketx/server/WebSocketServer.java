package com.jachs.netty.websocketx.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * An HTTP server which serves Web Socket requests at:
 *
 * http://localhost: /websocket
 *
 * Open your browser at <a href="http://localhost: /">http://localhost: /</a>,
 * then the demo page will be loaded and a Web Socket connection will be made
 * automatically.
 *
 * This server illustrates support for the different web socket specification
 * versions and will work with:
 *
 * <ul>
 * <li>Safari + (draft-ietf-hybi-thewebsocketprotocol- )
 * <li>Chrome - (draft-ietf-hybi-thewebsocketprotocol- )
 * <li>Chrome + (draft-ietf-hybi-thewebsocketprotocol- )
 * <li>Chrome + (RFC aka draft-ietf-hybi-thewebsocketprotocol- )
 * <li>Firefox + (draft-ietf-hybi-thewebsocketprotocol- )
 * <li>Firefox + (RFC aka draft-ietf-hybi-thewebsocketprotocol- )
 * </ul>
 */
public final class WebSocketServer {

	static final boolean SSL = System.getProperty("ssl") != null;
	static final int PORT = Integer.parseInt(System.getProperty("port", SSL? "8443" : "8080"));

	public static void main(String[] args) throws Exception {
		// Configure SSL.
		final SslContext sslCtx;
		if (SSL) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
		} else {
			sslCtx = null;
		}

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new WebSocketServerInitializer(sslCtx));

			Channel ch = b.bind(PORT).sync().channel();

			System.out.println(
					"Open your web browser and navigate to " + (SSL ? "https" : "http") + "://   . . . :" + PORT + '/');

			ch.closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}