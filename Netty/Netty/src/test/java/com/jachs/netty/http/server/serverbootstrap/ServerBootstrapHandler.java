package com.jachs.netty.http.server.serverbootstrap;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpHeaderValues.TEXT_PLAIN;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;

/**
 * @author zhanchaohan
 * 
 */
public class ServerBootstrapHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
    
    @Override
    protected void channelRead0 ( ChannelHandlerContext ctx , HttpObject msg ) throws Exception {
        if ( msg instanceof HttpRequest ) {
            HttpRequest req = (HttpRequest) msg;

            boolean keepAlive = HttpUtil.isKeepAlive ( req );

            FullHttpResponse response = new DefaultFullHttpResponse ( req.protocolVersion (), OK,
                    Unpooled.wrappedBuffer ( "返回一段文字".getBytes () ) );
            response.headers ().set ( CONTENT_TYPE, TEXT_PLAIN ).setInt ( CONTENT_LENGTH,
                    response.content ().readableBytes () );

            ChannelFuture f = ctx.write ( response );

            if (keepAlive) {
                if (!req.protocolVersion().isKeepAliveDefault()) {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                }
            } else {
                //告诉客户端不要关闭连接.
                response.headers().set(CONNECTION, CLOSE);
            }
            if ( !keepAlive ) {
                f.addListener ( ChannelFutureListener.CLOSE );
            }

        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
