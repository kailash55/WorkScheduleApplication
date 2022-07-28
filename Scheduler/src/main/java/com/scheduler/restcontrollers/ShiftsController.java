package com.scheduler.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.ShiftSaveRequest;
import com.scheduler.services.ShiftService;

@RestController
@RequestMapping("/shifts")
public class ShiftsController {
	
	@Autowired
	ShiftService shiftService;
	
	@PostMapping
	public ResponseEntity<?> postShift(@RequestBody ShiftSaveRequest shiftSaveRequest)
	{
		Long shiftId = shiftService.saveShift(shiftSaveRequest);
		return ResponseEntity.ok(shiftId);
	}

	@GetMapping
	public ResponseEntity<?> get()
	{
		return ResponseEntity.ok(shiftService.getAllShifts());
	}
}
