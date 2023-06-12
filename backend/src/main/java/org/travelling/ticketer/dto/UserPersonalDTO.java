package org.travelling.ticketer.dto;

import java.util.HashSet;
import java.util.Set;

public class UserPersonalDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private Set<String> acceptedNotificationTypes;

    public UserPersonalDTO() {
    }

    public Set<String> getAcceptedNotificationTypes() {
        return acceptedNotificationTypes;
    }

    public void setAcceptedNotificationTypes(Set<String> acceptedNotificationTypes) {
        this.acceptedNotificationTypes = acceptedNotificationTypes;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
