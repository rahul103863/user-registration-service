package com.xerris.registration;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.myorg.registration.util.ConfigUtility;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ConfigUtility configUtility;
	
	/**
	 * Method to test successful message.
	 * @throws Exception
	 */
	@Test
	public void shouldReturnSuccessMessage() throws Exception {
		this.mockMvc
				.perform(post("/registration")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n"
								+ "    \"username\": \"Rahul\",\n"
								+ "    \"password\": \"Passsss123_\",\n"
								+ "    \"userIp\": \"24.48.0.1\"\n"
								+ "}"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Welcome")));
	}
	
	/**
	 * Method to test invalid Password policy.
	 * @throws Exception
	 */
	@Test
	public void shouldReturnPasswordFailedMessage() throws Exception {
		this.mockMvc
				.perform(post("/registration")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n"
								+ "    \"username\": \"Rahul\",\n"
								+ "    \"password\": \"passsss123_\",\n"
								+ "    \"userIp\": \"24.48.0.1\"\n"
								+ "}"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(configUtility.getProperty("password.policy.error"))));
	}
	
	/**
	 * Method to test Ineligible user registration
	 * @throws Exception
	 */
	@Test
	public void shouldReturnEligibleFailedMessage() throws Exception {
		this.mockMvc
				.perform(post("/registration")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n"
								+ "    \"username\": \"Rahul\",\n"
								+ "    \"password\": \"Passsss123_\",\n"
								+ "    \"userIp\": \"49.205.225.207\"\n"
								+ "}"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(configUtility.getProperty("user.not.eligible"))));
	}

}
