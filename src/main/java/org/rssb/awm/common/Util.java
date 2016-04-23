package org.rssb.awm.common;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;


/**
 * Created by Sumiran Chugh on 3/28/2016.
 *
 * @copyright atlas
 */
public class Util {


    public static HttpEntity<String> addTokenToHeader(String username, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(
                auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String( encodedAuth );
        headers.set( "Authorization", authHeader );
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }

    public static HttpEntity<String> addTokenToHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set(Constants.ACCESS_TOKEN, token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return entity;
    }

    public static byte[] readFully(InputStream stream) throws IOException
    {
        byte[] buffer = new byte[8192];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int bytesRead;
        while ((bytesRead = stream.read(buffer)) != -1)
        {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }

    public static byte[] loadFile(String sourcePath) throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream = (new URL(sourcePath)).openStream();
            return readFully(inputStream);
        }
        finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
    }
}
