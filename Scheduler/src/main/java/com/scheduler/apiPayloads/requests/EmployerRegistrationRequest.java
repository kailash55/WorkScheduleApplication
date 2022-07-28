package com.scheduler.apiPayloads.requests;

import lombok.Data;

@Data
public class EmployerRegistrationRequest {
	private String organizationName;
	private String email;
	private String username;
	private String password;
}
