package org.rssb.awm.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Sumiran Chugh on 1/10/2016.
 */

public class HttpEntryPoint implements AuthenticationEntryPoint {

    private String bimsUrl;

    private String clientId;

    public HttpEntryPoint(String bimsUrl, String clientId) {
        this.bimsUrl = bimsUrl;
        this.clientId = clientId;
        assert bimsUrl != null;
        assert clientId != null;
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
       // if(authException instanceof BadCredentialsException)
          // response.sendRedirect("/");
       // else
   //     response.sendError(response.SC_RESET_CONTENT,);
//response.flushBuffer();

        response.setHeader("Content-Type", "text/plain");
        response.setHeader("redirect",new StringBuilder(bimsUrl).append("?continue=").append(clientId).toString() );
        response.setStatus(response.SC_RESET_CONTENT);
        PrintWriter writer = response.getWriter();
        writer.write(new StringBuilder(bimsUrl).append("?continue=").append(clientId).toString());
        writer.close();
     //  response.sendRedirect(new StringBuilder(bimsUrl).append("?continue=").append(clientId).toString());
    }
}
