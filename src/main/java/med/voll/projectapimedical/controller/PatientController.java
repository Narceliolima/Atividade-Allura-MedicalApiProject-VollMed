package med.voll.projectapimedical.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.projectapimedical.domain.patient.Patient;
import med.voll.projectapimedical.domain.patient.PatientDetailsData;
import med.voll.projectapimedical.domain.patient.PatientListingData;
import med.voll.projectapimedical.domain.patient.PatientRegistryData;
import med.voll.projectapimedical.domain.patient.PatientRepository;
import med.voll.projectapimedical.domain.patient.PatientUpdatingData;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "token-key")
public class PatientController {

	@Autowired
	private PatientRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Object> register(@RequestBody @Valid PatientRegistryData data, UriComponentsBuilder uriBuilder) {
		
		Patient patient = new Patient(data);
		repository.save(patient);
		
		URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(patient.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new PatientDetailsData(patient));
	}
	
	@GetMapping
	public ResponseEntity<Page<PatientListingData>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
		
		Page<PatientListingData> pageOfPatientList = repository.findAllByisActiveTrue(pageable).map(PatientListingData::new);
		return ResponseEntity.ok(pageOfPatientList);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<PatientDetailsData> update(@RequestBody @Valid PatientUpdatingData data) {
		
		Patient patient = repository.getReferenceById(data.id());
		patient.updateInfo(data);
		
		return ResponseEntity.ok(new PatientDetailsData(patient));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		
		Patient patient = repository.getReferenceById(id);
		patient.deactivate();
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PatientDetailsData> detail(@PathVariable Long id) {
		
		Patient patient = repository.getReferenceById(id);
		
		return ResponseEntity.ok(new PatientDetailsData(patient));
	}
}
