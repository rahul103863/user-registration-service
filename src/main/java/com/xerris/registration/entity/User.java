package com.xerris.registration.entity;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class User {
	
	private String username;
	private String password;
	private String userIp;
	
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
	public String getUserIp() {
		return userIp;
	}
	public void setuserIp(String userIp) {
		this.userIp = userIp;
	}
	@Override
	public int hashCode() {
		return Objects.hash(password, userIp, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(password, other.password) && Objects.equals(userIp, other.userIp)
				&& Objects.equals(username, other.username);
	}
}
