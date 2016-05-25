package org.rssb.awm.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Map;



/**
 * Created by Sumiran Chugh on 3/28/2016.
 *
 * @copyright atlas
 */
public class Util {

    static final Logger logger = LoggerFactory.getLogger(Util.class);

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

    public static String getVariable(Map<String, Object> inputMap, String varname) {
        return inputMap.get(varname) == null ? "" : inputMap.get(varname).toString();
    }

    public static <T> T getVariable(Map<String, Object> inputMap, String varname, Class<T> t) {
        try {
            T t1 = t.cast(inputMap.get(varname));
            return t1 == null ? t.newInstance() : t1;
        } catch (ClassCastException e) {
            logger.error(LogMessages.ERR_FETCH_VAR_CAST, varname);
        } catch (InstantiationException e) {
            logger.error(LogMessages.ERR_FETCH_VAR_INST, varname);
        } catch (IllegalAccessException e) {
            logger.error(LogMessages.ERR_FETCH_VAR_INST, varname);
        }
        logger.info(LogMessages.WARN_RETURN_VAR_NULL, varname);
        return null;
    }
}
