package med.voll.projectapimedical.domain.patient;

public record PatientListingData(Long id,
								String name,
								String email,
								String cpf) {
	
	public PatientListingData(Patient medic) {
		this(medic.getId(), medic.getName(), medic.getEmail(), medic.getCpf());
	}
}
