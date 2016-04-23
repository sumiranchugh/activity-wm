package org.rssb.awm.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Sumiran Chugh on 1/12/2016.
 */
public class BimsPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {


    @Value("${bims.token}")
    private String tokenid;

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        Object token = request.getParameter(tokenid) ;
        if(token==null && request.getHeader("name")!=null)
    return request.getHeader("name");
        return token;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        String username  =request.getHeader("name");
        String password  =request.getHeader("pass");
        return new UsernamePasswordAuthenticationToken(username,password);
    }
}
