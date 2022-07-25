package com.xerris.registration.service;

import com.xerris.registration.entity.RegistrationStatus;
import com.xerris.registration.entity.User;

public interface RegistrationService {
	
	public RegistrationStatus getRegistrationStatus(User user);

}
