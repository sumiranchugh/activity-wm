package org.rssb.awm.conrollers;

import org.activiti.engine.IdentityService;
import org.rssb.awm.processes.listeners.PreBimsListner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;


@Controller
public class HomeController {

    @Autowired
    IdentityService identityService;

    @RequestMapping(value = {"/getLoggedInUser"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Object getLoggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null || authentication.getPrincipal().toString().equalsIgnoreCase("anonymoususer"))
            return null;
        return authentication.getPrincipal();
    }

    /*@RequestMapping({"/views/{view}"})
    public String getView(HttpServletRequest request, @PathVariable String view) {
       Authentication auth =SecurityContextHolder.getContext().getAuthentication();
        if(auth==null || auth.getPrincipal()==null)
            return "/401";
        String p = (String)auth.getPrincipal();
        if((scopeMap.get(p)!=null && scopeMap.get(p).contains(view)) || scopeMap.get("any").contains(view))
        return "/views/"+view;
        return "/401";
    }*/

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        request.getSession().invalidate();
    }

    @RequestMapping("/login**")
    public String login() {

        return "login";
    }

    @RequestMapping("/")
    public String root() {

        return "home";
    }
    @RequestMapping(value = "/users",method = RequestMethod.POST)
    @ResponseBody()
    public PreBimsListner.Users mapTest(){
        PreBimsListner.Users users = new PreBimsListner.Users();
        Set<String> set  = new HashSet<>();
        set.add("G09663");
        set.add("user2");
        users.getMap().put("CENTER",set );
        users.getMap().put("AREA",set );
        return users;
    }


}
