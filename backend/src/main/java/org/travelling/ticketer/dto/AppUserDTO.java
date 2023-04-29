package org.travelling.ticketer.dto;


import java.util.Set;

public class AppUserDTO {


    private Set<String> roles;
    private UserPersonalDTO personalData;
    private UserCredentialsDTO userCredentials;

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
