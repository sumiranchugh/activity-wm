package org.rssb.awm.security;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rssb.awm.common.Util;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;


public class BimsAuthUserDetailsService implements AuthenticationUserDetailsService {

    private static final Log logger = LogFactory.getLog(BimsAuthUserDetailsService.class);


    IdentityService identityService;

    private String bimsValidateUrl;

    private RestTemplate restTemplate = new RestTemplate();
    private UserDetails userDetails = null;

    boolean active = true;


    public BimsAuthUserDetailsService(String bimsValidateUrl, IdentityService identityService) {
        this.bimsValidateUrl = bimsValidateUrl;
        this.identityService = identityService;
        assert bimsValidateUrl != null && identityService != null;
    }

    @Override
    public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {

        ResponseEntity<org.rssb.awm.security.types.UserDetails> response = null;

        if (((UsernamePasswordAuthenticationToken) (authentication.getCredentials())).getCredentials() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Attempting login via token");
            }
            HttpEntity<String> entity = Util.addTokenToHeader((String) authentication.getPrincipal());
            try {
                response = restTemplate.
                        exchange(bimsValidateUrl, HttpMethod.GET, entity, org.rssb.awm.security.types.UserDetails.class, authentication.getPrincipal());
            } catch (Exception e) {
                throw new UsernameNotFoundException("Error validating token");
            }
            boolean notvalid = response.getBody().isHasError();
            if (notvalid)
                throw new UsernameNotFoundException("Username not found in BIMS");
            response.getBody().getAdditionalProperties().put("scope", false);
            userDetails = response.getBody();
            identityService.setAuthenticatedUserId(((org.rssb.awm.security.types.UserDetails) userDetails).getUser().getUserId());

        } else {
            if (authentication.getCredentials() instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken credentials = (UsernamePasswordAuthenticationToken) authentication.getCredentials();
                User user = identityService.createUserQuery().userId((String) credentials.getPrincipal()).singleResult();
                if (user == null)
                    throw new BadCredentialsException("Invalid Credentials");
                if (user.getPassword().equals((String) credentials.getCredentials())) ;
                identityService.setAuthenticatedUserId(user.getId());

                userDetails = new org.rssb.awm.security.types.UserDetails(user, true);
            } else throw new BadCredentialsException("Invalid Credentials");
        }
        authentication.setAuthenticated(true);

        return userDetails;
    }


}
