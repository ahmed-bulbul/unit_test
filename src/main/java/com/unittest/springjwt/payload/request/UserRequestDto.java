package com.unittest.springjwt.payload.request;

public class UserRequestDto {

    private String username;
    private String email;
    private String password;
    private String role;

    public UserRequestDto(String testuser, String mail) {
        this.username = testuser;
        this.email = mail;

    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
