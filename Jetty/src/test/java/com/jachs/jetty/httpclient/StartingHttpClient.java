package com.jachs.jetty.httpclient;

import org.junit.jupiter.api.Test;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

import org.eclipse.jetty.client.HttpClient;

/**
 * @author zhanchaohan
 * @see https://www.eclipse.org/jetty/documentation/jetty-11/programming_guide.php
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
    /***
     * Jetty的HttpClient支持开箱即用的cookies。
       HttpClient实例从HTTP响应接收cookie，并将它们存储在java.net.CookieStore存储，
                    一个属于JDK的类。当发出新请求时，会查询cookie存储区，如果存在匹配的cookie
                    （即，未过期且与请求的域和路径匹配的cookie），则会将它们添加到请求中。
                    应用程序可以通过编程方式访问cookie存储以查找已设置的cookie：
     */
    @Test
    public void test2() {
        HttpClient httpClient = new HttpClient ();
        
        CookieStore cookieStore = httpClient.getCookieStore();
        List<HttpCookie> cookies = cookieStore.get(URI.create("http://www.baidu.com"));
        
        for (HttpCookie httpCookie : cookies ) {
            System.out.println ( httpCookie.getComment () );
        }
    }
}
