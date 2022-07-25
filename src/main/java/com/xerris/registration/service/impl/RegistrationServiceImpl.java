package com.xerris.registration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.xerris.registration.controller.RegistrationController;
import com.xerris.registration.entity.GeoLocationDetail;
import com.xerris.registration.entity.RegistrationStatus;
import com.xerris.registration.entity.User;
import com.xerris.registration.service.RegistrationService;
import com.xerris.registration.util.ConfigUtility;
import com.xerris.registration.util.PasswordPolicyValidator;

import lombok.Value;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class RegistrationServiceImpl implements RegistrationService{
	
	
	private static final  Logger log = LoggerFactory.getLogger(RegistrationServiceImpl.class);
	
	@Autowired
	ConfigUtility configUtility;

	@Override
	public RegistrationStatus getRegistrationStatus(User user) {
		
		log.info("Inside getRegistrationStatus method");
		String ipApiUrl = configUtility.getProperty("ip.api.url");
		RegistrationStatus registrationStatus = new RegistrationStatus();
		String responseMessage = null;
		String regStatus = null;
			
		ResponseEntity<GeoLocationDetail> responseEntity = 
		   new RestTemplate().getForEntity(ipApiUrl, GeoLocationDetail.class, user.getUserIp());
		GeoLocationDetail geoLocationDetail = responseEntity.getBody();
		
		if(validatePasswordPolicy(user.getPassword())) {
			if(geoLocationDetail != null && geoLocationDetail.getCountry().equals(configUtility.getProperty("user.ip.country"))) {
				responseMessage = MessageFormat.format(configUtility.getProperty("user.eligible.message"), user.getUsername(),geoLocationDetail.getCity());
				regStatus = "Success";
			} else {
				responseMessage = configUtility.getProperty("user.not.eligible");
				regStatus = "Falied";
			}
		} else {
			responseMessage = configUtility.getProperty("password.policy.error");
			regStatus = "Failed";
		}
		registrationStatus.setMessage(responseMessage);
		registrationStatus.setStatus(regStatus);
		return registrationStatus;
	}
	
	private boolean validatePasswordPolicy(String password) {
		log.info("Inside validatePasswordPolicy method");
		return PasswordPolicyValidator.isValid(password, configUtility.getProperty("password.pattern"));
	}

}