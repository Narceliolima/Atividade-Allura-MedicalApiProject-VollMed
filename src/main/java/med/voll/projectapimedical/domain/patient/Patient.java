package med.voll.projectapimedical.domain.patient;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.projectapimedical.domain.address.Address;

@Entity 
@Table(name = "patients")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String telephone;
	private String cpf;
	
	@Embedded
	private Address address;
	
	@Column(name = "active_account")
	private Boolean isActive;
	
	public Patient(PatientRegistryData patientRegistryData) {
		this.isActive = true;
		this.name = patientRegistryData.name();
		this.email = patientRegistryData.email();
		this.telephone = patientRegistryData.telephone();
		this.cpf = patientRegistryData.cpf();
		this.address = new Address(patientRegistryData.address());
	}

	public void updateInfo(@Valid PatientUpdatingData data) {
		
		if(data.name()!=null) {
			this.name = data.name();
		}
		if(data.telephone()!=null) {
			this.telephone = data.telephone();
		}
		if(data.address()!=null) {
			this.address.updateInfo(data.address());
		}
	}

	public void deactivate() {
		
		this.isActive = false;
	}
}
