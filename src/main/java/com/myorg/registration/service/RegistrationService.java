package com.myorg.registration.service;

import com.myorg.registration.entity.RegistrationStatus;
import com.myorg.registration.entity.User;

public interface RegistrationService {
	
	public RegistrationStatus getRegistrationStatus(User user);

}
