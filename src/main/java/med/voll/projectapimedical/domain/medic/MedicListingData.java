package med.voll.projectapimedical.domain.medic;

public record MedicListingData(Long id,
								String name,
								String email,
								String crm,
								Speciality speciality) {
	
	public MedicListingData(Medic medic) {
		this(medic.getId(), medic.getName(), medic.getEmail(), medic.getCrm(), medic.getSpeciality());
	}
}
