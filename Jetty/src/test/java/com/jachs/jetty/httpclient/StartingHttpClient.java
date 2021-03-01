package com.jachs.jetty.httpclient;

import org.junit.jupiter.api.Test;
import org.eclipse.jetty.client.HttpClient;

/**
 * @author zhanchaohan
 * 
 */
public class StartingHttpClient {

    @Test
    public void test1 () throws Exception {
        // Instantiate HttpClient.
        HttpClient httpClient = new HttpClient ();

        // Configure HttpClient, for example:
        httpClient.setFollowRedirects ( false );

        // Start HttpClient.
        httpClient.start ();
    }
}
