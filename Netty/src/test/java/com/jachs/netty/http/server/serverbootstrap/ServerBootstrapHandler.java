package com.jachs.netty.http.server.serverbootstrap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;

/**
 * @author zhanchaohan
 * 
 */
public class ServerBootstrapHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0 ( ChannelHandlerContext ctx , HttpObject msg ) throws Exception {
        if ( msg instanceof HttpRequest ) {
            HttpRequest req = (HttpRequest) msg;
            
            boolean keepAlive = HttpUtil.isKeepAlive(req);
            
            System.out.println ( keepAlive );
            ctx.write ( "ACCC" );

            DecoderResult decoderResult = msg.getDecoderResult ();

            System.out.println ( decoderResult.isFailure () );
            System.out.println ( decoderResult.isFinished () );
            System.out.println ( decoderResult.isSuccess () );
        }
    }

}
