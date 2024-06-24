package med.voll.projectapimedical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.projectapimedical.domain.appointment.AppointmentCancelData;
import med.voll.projectapimedical.domain.appointment.AppointmentDetailsData;
import med.voll.projectapimedical.domain.appointment.AppointmentScheduleData;
import med.voll.projectapimedical.domain.appointment.AppointmentService;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "token-key")
public class AppointmentController {
	
	@Autowired
	private AppointmentService service;

	@PostMapping
	@Transactional
	public ResponseEntity<Object> schedule(@RequestBody @Valid AppointmentScheduleData data){
		
		AppointmentDetailsData dto = service.appointmentSchedule(data);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping
	@Transactional
	public ResponseEntity<Object> cancel(@RequestBody @Valid AppointmentCancelData data){
		
		service.appointmentCancel(data);
		return ResponseEntity.noContent().build();
	}
 }
