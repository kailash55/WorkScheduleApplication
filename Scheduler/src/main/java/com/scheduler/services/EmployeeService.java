package com.scheduler.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scheduler.apiPayloads.requests.SaveEmployeeRequest;
import com.scheduler.apiPayloads.responses.EmployeeDataResponse;
import com.scheduler.mappers.EmployeeDataResponseMapper;
import com.scheduler.models.ERole;
import com.scheduler.models.Employee;
import com.scheduler.models.Role;
import com.scheduler.models.User;
import com.scheduler.repository.EmployeeRepository;
import com.scheduler.repository.PositionRepository;
import com.scheduler.repository.RoleRepository;
import com.scheduler.repository.UserRepository;

@Service
public class EmployeeService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	EmployeeRepository employeeRepo;
	
	@Autowired
    PasswordEncoder encoder;
	
	@Autowired
    RoleRepository roleRepository;
	
	@Autowired
    UserRepository userRepository;	
	
	@Autowired
	PositionRepository positionRepo;
	
	public Long saveEmployee(SaveEmployeeRequest saveEmployeeRequest)
	{
		UserDetails userDetails =
				(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		User user = new User(saveEmployeeRequest.getUsername(),
				saveEmployeeRequest.getEmail(),
                encoder.encode("htmldata"));
		
		Set<Role> roles = new HashSet<>();
		Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(adminRole);
        
        user.setRoles(roles);
        user
		.setOrganization
		(userRepo.findByUsername
				(userDetails.getUsername()).get().getOrganization());
        userRepository.save(user);
        
        
		Employee employee = new Employee();
		employee.setFirstName(saveEmployeeRequest.getFirstName());
		employee.setLastName(saveEmployeeRequest.getLastName());
		employee.setEmail(saveEmployeeRequest.getEmail());
		employee.setMobileNumber(saveEmployeeRequest.getMobileNumber());
		employee.setWeeklyWorkingHours(saveEmployeeRequest.getWeeklyWorkingHours());
		employee.setPositions(positionRepo.findAllById(saveEmployeeRequest.getPositionIds()));
		employee.setUser(user);
		
		employee.setCreatedBy(userRepo.findByUsername(userDetails.getUsername()).get());
		employee.setCreatedDateTime(new Date());
		employee
		.setOrganization
		(userRepo.findByUsername
				(userDetails.getUsername()).get().getOrganization());
		
		employeeRepo.save(employee);
		return employee.getId();
	}
	
	
	public List<EmployeeDataResponse> getAllEmployees()
	{
		return new EmployeeDataResponseMapper().map(employeeRepo.findAll());
	}
}
