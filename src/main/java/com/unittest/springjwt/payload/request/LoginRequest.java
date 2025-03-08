package com.unittest.springjwt.payload.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	
	@NotBlank
	private String username;

	@NotBlank
	private String password;

    public LoginRequest(String testuser, String password) {
		this.username = testuser;
		this.password = password;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
