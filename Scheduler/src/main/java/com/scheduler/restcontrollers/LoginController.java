package com.scheduler.restcontrollers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.LoginRequest;
import com.scheduler.apiPayloads.responses.JwtResponse;
import com.scheduler.models.Employee;
import com.scheduler.repository.EmployeeRepository;
import com.scheduler.services.UserDetailsImpl;
import com.scheduler.util.jwt.JwtUtils;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/login")
public class LoginController{
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	EmployeeRepository empRepo;
	
	@Autowired
    JwtUtils jwtUtils;

	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
	{
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        
        Employee emp = empRepo.findByUserId(userDetails.getId());
        
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                emp == null ? null : emp.getId(),
                roles));
	}
}
