package com.myorg.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myorg.registration.entity.RegistrationStatus;
import com.myorg.registration.entity.User;
import com.myorg.registration.service.RegistrationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/registration")
@Slf4j
public class RegistrationController {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	private RegistrationService registrationService;
	
	/**
	 * 
	 * @param user
	 * @return RegistrationStatus
	 */
	@PostMapping
	public RegistrationStatus registerUser(@RequestBody User user ) {
		log.info("Inside registerUser method");

		return registrationService.getRegistrationStatus(user);
	}
	
	
	/**
	 * Catch all Runtime Exceptions
	 * @param ex
	 * @return RegistrationStatus
	 */
	@ExceptionHandler(RuntimeException.class)
	public final RegistrationStatus handleAllExceptions(RuntimeException ex) {
		log.error("Inside handleAllExceptions method");
		RegistrationStatus registrationStatus = new RegistrationStatus();
		registrationStatus.setMessage("Internal Server Error.");
		registrationStatus.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
	    return registrationStatus;
	}
}
