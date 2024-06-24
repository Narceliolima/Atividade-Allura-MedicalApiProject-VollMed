package med.voll.projectapimedical.domain.medic;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity //Dá pra por um nome na entidade
@Table(name = "medics")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String telephone;
	private String crm;
	
	@Enumerated(EnumType.STRING)
	private Speciality speciality;
	
	@Embedded
	private Address address;
	
	@Column(name = "active_account")
	private Boolean isActive;
	
	public Medic(MedicRegistryData medicRegistryData) {
		this.isActive = true;
		this.name = medicRegistryData.name();
		this.email = medicRegistryData.email();
		this.telephone = medicRegistryData.telephone();
		this.crm = medicRegistryData.crm();
		this.speciality = medicRegistryData.speciality();
		this.address = new Address(medicRegistryData.address());
	}

	public void updateInfo(@Valid MedicUpdatingData data) {
		
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
