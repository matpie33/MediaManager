package org.media.manager.dto;

public class AppUserDTO {

    private String userName;
    private String password;
    private UserPersonalDTO personalData;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserPersonalDTO getPersonalData() {
        return personalData;
    }
}
