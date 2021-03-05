package com.jachs.jetty.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author zhanchaohan
 * 
 */
public class StartingServer {
    
    @Test
    public void test1 () throws Exception {
        // Create and configure a ThreadPool.
        QueuedThreadPool threadPool = new QueuedThreadPool ();
        threadPool.setName ( "server" );

        // Create a Server instance.
        Server server = new Server ( threadPool );

        // Create a ServerConnector to accept connections from clients.
        Connector connector = new ServerConnector ( server );

        // Add the Connector to the Server
        server.addConnector ( connector );

        // Set a simple Handler to handle requests/responses.
        server.setHandler ( new AbstractHandler () {
            @Override
            public void handle ( String target , Request jettyRequest , HttpServletRequest request ,
                    HttpServletResponse response ) {
                // Mark the request as handled so that it
                // will not be processed by other handlers.
                jettyRequest.setHandled ( true );
            }
        } );

        // Start the Server so it starts accepting connections from clients.
        server.start ();
    }
}
