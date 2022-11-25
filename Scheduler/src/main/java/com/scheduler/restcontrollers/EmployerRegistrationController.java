package com.scheduler.restcontrollers;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.EmployerRegistrationRequest;
import com.scheduler.apiPayloads.responses.MessageResponse;
import com.scheduler.models.ERole;
import com.scheduler.models.Organization;
import com.scheduler.models.Role;
import com.scheduler.models.User;
import com.scheduler.repository.OrganizationRepository;
import com.scheduler.repository.RoleRepository;
import com.scheduler.repository.UserRepository;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/employeeregistration")
public class EmployerRegistrationController {

	@Autowired
    PasswordEncoder encoder;
	@Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    OrganizationRepository organizationRepository;
	
    @PostMapping
    @Transactional
	public ResponseEntity<?> registerEmployer(@RequestBody EmployerRegistrationRequest registrationRequest)
	{

        Organization organization = new Organization();
        organization.setName(registrationRequest.getOrganizationName());
        organization.setCreatedDateTime(new Date());
        organizationRepository.save(organization);
        
        User user = new User(registrationRequest.getUsername(),
				registrationRequest.getEmail(),
                encoder.encode(registrationRequest.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(adminRole);
        
        user.setRoles(roles);
        user.setOrganization(organization);
        userRepository.save(user);
       
        return ResponseEntity.ok(new MessageResponse("Organization registered successfully!"));
	}
    
    
	
}
