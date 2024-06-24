package med.voll.projectapimedical.domain.medic;

import jakarta.validation.constraints.NotNull;
import med.voll.projectapimedical.domain.address.AddressData;

public record MedicUpdatingData(@NotNull Long id,
								String name,
								String telephone,
								AddressData address) {

}
