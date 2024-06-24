package med.voll.projectapimedical.domain.patient;

import med.voll.projectapimedical.domain.address.Address;

public record PatientDetailsData(String name,
								String email,
								String telephone,
								String cpf,
								Address address) {
	
	public PatientDetailsData(Patient medic) {
		this(medic.getName(), medic.getEmail(), medic.getTelephone(), medic.getCpf(), medic.getAddress());
	}
}
