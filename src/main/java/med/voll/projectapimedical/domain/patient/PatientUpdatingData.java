package med.voll.projectapimedical.domain.patient;

import jakarta.validation.constraints.NotNull;
import med.voll.projectapimedical.domain.address.AddressData;

public record PatientUpdatingData(@NotNull Long id,
								String name,
								String telephone,
								AddressData address) {

}
