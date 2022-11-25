package com.scheduler.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.AddOpenShiftRequest;
import com.scheduler.apiPayloads.requests.AssignShiftRequest;
import com.scheduler.services.AssignedShiftService;
import com.scheduler.services.OpenShiftService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/open-shifts")
public class OpenShiftsController {

	@Autowired
	OpenShiftService openShiftService;
	
	@PostMapping
	public ResponseEntity<?> assignOpenShift(@RequestBody AddOpenShiftRequest openShiftRequest)
	{
		List<Long> ids = openShiftService.addOpenShift(openShiftRequest);
		return ResponseEntity.ok(ids);
	}
	
}
