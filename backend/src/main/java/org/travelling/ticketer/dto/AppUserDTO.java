package org.travelling.ticketer.dto;

public class AppUserDTO {


    private UserPersonalDTO personalData;
    private UserCredentialsDTO userCredentials;

    public UserPersonalDTO getPersonalData() {
        return personalData;
    }

    public UserCredentialsDTO getUserCredentials() {
        return userCredentials;
    }
}
