package med.voll.projectapimedical.domain.medic;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.projectapimedical.domain.address.AddressData;

public record MedicRegistryData(@NotBlank(message =  "{name.required}") String name,
								@NotBlank(message =  "{email.required}") @Email(message =  "{email.invalid}") String email,
								@NotBlank(message =  "{telephone.required}") String telephone,
								@NotBlank(message =  "{crm.required}") @Pattern(regexp = "\\d{4,6}", message =  "{crm.invalid}") String crm,
								@NotNull(message =  "{speciality.required}") Speciality speciality,
								@NotNull(message =  "{address.required}") @Valid AddressData address) {

}
