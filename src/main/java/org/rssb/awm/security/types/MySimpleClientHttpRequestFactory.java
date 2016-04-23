package org.rssb.awm.security.types;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.HttpURLConnection;

public class MySimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

    private  HostnameVerifier verifier=new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
        ;

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (connection instanceof HttpsURLConnection) {
            ((HttpsURLConnection) connection).setHostnameVerifier(verifier);
        }
        super.prepareConnection(connection, httpMethod);
    }

}
