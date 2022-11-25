package com.scheduler.restcontrollers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.AssignShiftRequest;
import com.scheduler.services.AssignedShiftService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/assigned-shifts")
public class AssignedShiftsController {

	@Autowired
	AssignedShiftService assignedShiftService;
	
	@PostMapping
	public ResponseEntity<?> assignShift(@RequestBody AssignShiftRequest assignShiftRequest) throws IOException
	{
		try {
			List<Long> ids = assignedShiftService.assignShifts(assignShiftRequest);
			return ResponseEntity.ok(ids);
		}
		catch(Exception e)
		{
//			StringWriter sw = new StringWriter();
//			PrintWriter pw = new PrintWriter(sw);
//			e.printStackTrace(pw);
//			String s = sw.toString();
//			sw.close();
//			pw.close();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
