package org.rssb.awm.security.types;

import com.fasterxml.jackson.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.Generated;
import java.util.*;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "Error",
        "ErrorSummary",
        "Errors",
        "HasError",
        "User"
})
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
//public class UserDetails  {

    @JsonProperty("Error")
    private Object Error;
    @JsonProperty("ErrorSummary")
    private String ErrorSummary;
    @JsonProperty("Errors")
    private List<Object> Errors = new ArrayList<Object>();
    @JsonProperty("HasError")
    private boolean HasError= false;
    @JsonProperty("User")
    private User User;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public UserDetails(org.activiti.engine.identity.User userDetails, boolean scope) {

        additionalProperties.put("scope",scope);
       if(getUser()==null){

           setUser(   new User(userDetails.getId(),userDetails.getFirstName()+userDetails.getLastName(),new ArrayList<Role>(),additionalProperties));
       }

    }

    public UserDetails(){}

    /**
     *
     * @return
     * The Error
     */
    @JsonProperty("Error")
    public Object getError() {
        return Error;
    }

    /**
     *
     * @param Error
     * The Error
     */
    @JsonProperty("Error")
    public void setError(Object Error) {
        this.Error = Error;
    }

    /**
     *
     * @return
     * The ErrorSummary
     */
    @JsonProperty("ErrorSummary")
    public String getErrorSummary() {
        return ErrorSummary;
    }

    /**
     *
     * @param ErrorSummary
     * The ErrorSummary
     */
    @JsonProperty("ErrorSummary")
    public void setErrorSummary(String ErrorSummary) {
        this.ErrorSummary = ErrorSummary;
    }

    /**
     *
     * @return
     * The Errors
     */
    @JsonProperty("Errors")
    public List<Object> getErrors() {
        return Errors;
    }

    /**
     *
     * @param Errors
     * The Errors
     */
    @JsonProperty("Errors")
    public void setErrors(List<Object> Errors) {
        this.Errors = Errors;
    }

    /**
     *
     * @return
     * The HasError
     */
    @JsonProperty("HasError")
    public boolean isHasError() {
        return HasError;
    }

    /**
     *
     * @param HasError
     * The HasError
     */
    @JsonProperty("HasError")
    public void setHasError(boolean HasError) {
        this.HasError = HasError;
    }

    /**
     *
     * @return
     * The User
     */
    @JsonProperty("User")
    public User getUser() {
        return User;
    }

    /**
     *
     * @param User
     * The User
     */
    @JsonProperty("User")
    public void setUser(User User) {
        this.User = User;
    }



    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUser().getRoles().stream().map((p) -> {
            GrantedAuthority ga = ()-> p.getRoleName();
            return ga;
        }).collect(Collectors.toList());
    }




    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return getUser().getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}