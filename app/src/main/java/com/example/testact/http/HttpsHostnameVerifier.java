package com.example.testact.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;


public class HttpsHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
