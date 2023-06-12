package org.travelling.ticketer.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column()
    private String phoneNumber;

    @ManyToMany
    private Set<Role> roles;

    @ManyToMany
    private Set<Notification> acceptedNotificationTypes;

    public void setAcceptedNotificationTypes(Set<Notification> acceptedNotificationTypes) {
        this.acceptedNotificationTypes = acceptedNotificationTypes;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Notification> getAcceptedNotificationTypes() {

        return acceptedNotificationTypes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setRoles(Set<Role> role) {
        this.roles = role;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
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
}
