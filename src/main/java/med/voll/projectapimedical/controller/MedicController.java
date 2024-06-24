package med.voll.projectapimedical.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
import med.voll.projectapimedical.domain.medic.Medic;
import med.voll.projectapimedical.domain.medic.MedicDetailsData;
import med.voll.projectapimedical.domain.medic.MedicListingData;
import med.voll.projectapimedical.domain.medic.MedicRegistryData;
import med.voll.projectapimedical.domain.medic.MedicRepository;
import med.voll.projectapimedical.domain.medic.MedicUpdatingData;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "token-key")
public class MedicController {

	@Autowired
	private MedicRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Object> register(@RequestBody @Valid MedicRegistryData data, UriComponentsBuilder uriBuilder) {
		
		Medic medic = new Medic(data);
		repository.save(medic);
		
		URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medic.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new MedicDetailsData(medic));
	}
	
	@GetMapping //@Secured("ROLE_ADMIN")
	public ResponseEntity<Page<MedicListingData>> list(@PageableDefault(size = 10, sort = {"name"}, direction = Direction.DESC) Pageable pageable){ //size=1&page=2&sort"name.desc"
		
		Page<MedicListingData> pageOfMedicList = repository.findAllByIsActiveTrue(pageable).map(MedicListingData::new);
		return ResponseEntity.ok(pageOfMedicList);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<MedicDetailsData> update(@RequestBody @Valid MedicUpdatingData data) {
		
		Medic medic = repository.getReferenceById(data.id());
		medic.updateInfo(data);
		
		return ResponseEntity.ok(new MedicDetailsData(medic));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		
		//repository.deleteById(id); //Exclui de verdade...
		Medic medic = repository.getReferenceById(id);
		medic.deactivate();
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> detail(@PathVariable Long id) {
		
		Medic medic = repository.getReferenceById(id);
		
		return ResponseEntity.ok(medic);
	}
 }
