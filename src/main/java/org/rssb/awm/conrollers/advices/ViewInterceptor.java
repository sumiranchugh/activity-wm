package org.rssb.awm.conrollers.advices;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sumiran Chugh on 3/30/2016.
 *
 * @copyright atlas
 */

public class ViewInterceptor extends HandlerInterceptorAdapter {

    Map<String, Set<String>> scopeMap = new HashMap<>();

    @PostConstruct
    public void initScope() {
        Set<String> links = new HashSet<>();
        links.add("dashboard");
        links.add("tasks");
        links.add("login");
        scopeMap.put("any", links);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        for (String any : scopeMap.get("any")) {
            if (request.getServletPath().contains(any))
                return true;
        }
        for (String any : scopeMap.get(((UserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
                )
        {
            if (request.getServletPath().contains(any))
                return true;
        }
        return false;
    }
}
