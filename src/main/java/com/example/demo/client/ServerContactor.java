package com.example.demo.client;

import org.springframework.web.client.RestTemplate;

public class ServerContactor {
    private static RestTemplate server = new RestTemplate();
    private static ServerContactor ins = null;
    private static String url;
    private ServerContactor() {
        server = new RestTemplate();
        url = "http://localhost:8080";
    }

    public static ServerContactor getInstance() {
        if (ins == null) {
            ins = new ServerContactor();
        }
        return ins;
    }

    public RestTemplate getServer(){
        return server;
    }

    public String getUrl() {
        return url;
    }
}
