package com.myorg.registration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.myorg.registration.controller.RegistrationController;
import com.myorg.registration.entity.GeoLocationDetail;
import com.myorg.registration.entity.RegistrationStatus;
import com.myorg.registration.entity.User;
import com.myorg.registration.service.RegistrationService;
import com.myorg.registration.util.ConfigUtility;
import com.myorg.registration.util.PasswordPolicyValidator;

import lombok.Value;

import java.text.MessageFormat;
import java.util.UUID;

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
		
		if(!validateIpAddress(user.getUserIp())) {
			registrationStatus.setMessage(configUtility.getProperty("user.not.eligible"));
			registrationStatus.setStatus("Failed");
			return registrationStatus;
		}
		ResponseEntity<GeoLocationDetail> responseEntity = 
		   new RestTemplate().getForEntity(ipApiUrl, GeoLocationDetail.class, user.getUserIp());
		GeoLocationDetail geoLocationDetail = responseEntity.getBody();
		
		if(validatePasswordPolicy(user.getPassword())) {
			if(geoLocationDetail != null && geoLocationDetail.getCountry().equals(configUtility.getProperty("user.ip.country"))) {
				responseMessage = MessageFormat.format(configUtility.getProperty("user.eligible.message"), user.getUsername(),geoLocationDetail.getCity());
				regStatus = "Success";
			} else {
				responseMessage = configUtility.getProperty("user.not.eligible");
				regStatus = "Failed";
			}
		} else {
			responseMessage = configUtility.getProperty("password.policy.error");
			regStatus = "Failed";
		}
		registrationStatus.setMessage(responseMessage);
		registrationStatus.setStatus(regStatus);
		if (regStatus.equalsIgnoreCase("Success")) {
			registrationStatus.setUuid(UUID.randomUUID().toString());
		}
		return registrationStatus;
	}
	
	private boolean validatePasswordPolicy(String password) {
		log.info("Inside validatePasswordPolicy method");
		return PasswordPolicyValidator.isValid(password, configUtility.getProperty("password.pattern"));
	}
	
	private boolean validateIpAddress(String ip) {
		
		if(ip == null  || ip.length() == 0) {
			return false;
		}
		return true;
	}

}
