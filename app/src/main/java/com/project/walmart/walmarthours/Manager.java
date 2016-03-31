package com.project.walmart.walmarthours;

import java.util.UUID;

/**
 * Created by bjoshi on 3/30/16.
 */
public class Manager {
    private UUID id = null;
    private String firstName = null;
    private String lastName = null;
    private String email = null;
    private String phone = null;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private static String JSON = "{\"firstName\":\"F_N\",\"lastName\":\"L_N\",\"email\":\"EMAIL\",\"phone\":\"PHONE\"}";
    public String toString() {
        String lJson = JSON;
        lJson = lJson.replaceAll("F_N", firstName);
        lJson = lJson.replaceAll("L_N", lastName);
        lJson = lJson.replaceAll("EMAIL", email);
        lJson = lJson.replaceAll("PHONE", phone);
        return lJson;
    }
}
