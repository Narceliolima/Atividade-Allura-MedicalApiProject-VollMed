package med.voll.projectapimedical.domain.medic;

import med.voll.projectapimedical.domain.address.Address;

public record MedicDetailsData(String name,
								String email,
								String telephone,
								String crm,
								Speciality speciality,
								Address address) {
	
	public MedicDetailsData(Medic medic) {
		this(medic.getName(), medic.getEmail(), medic.getTelephone(), medic.getCrm(), medic.getSpeciality(), medic.getAddress());
	}
}
