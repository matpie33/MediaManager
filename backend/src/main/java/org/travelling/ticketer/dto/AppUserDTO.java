package org.travelling.ticketer.dto;


import java.util.Set;

public class AppUserDTO {


    private Set<String> roles;
    private UserPersonalDTO personalData;
    private UserCredentialsDTO userCredentials;

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public void setPersonalData(UserPersonalDTO personalData) {
        this.personalData = personalData;
    }

    public void setUserCredentials(UserCredentialsDTO userCredentials) {
        this.userCredentials = userCredentials;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public UserPersonalDTO getPersonalData() {
        return personalData;
    }

    public UserCredentialsDTO getUserCredentials() {
        return userCredentials;
    }
}
