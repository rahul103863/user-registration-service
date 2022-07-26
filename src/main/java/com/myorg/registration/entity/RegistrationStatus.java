package com.myorg.registration.entity;

import java.util.Objects;

public class RegistrationStatus {
	
	private String message;
	private String status;
	private String uuid;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Override
	public int hashCode() {
		return Objects.hash(message, status, uuid);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationStatus other = (RegistrationStatus) obj;
		return Objects.equals(message, other.message) && Objects.equals(status, other.status) && Objects.equals(uuid, other.uuid);
	}
}
