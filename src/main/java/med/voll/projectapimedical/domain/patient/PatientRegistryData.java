package med.voll.projectapimedical.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.projectapimedical.domain.address.AddressData;

public record PatientRegistryData(@NotBlank String name,
								@NotBlank @Email String email,
								@NotBlank String telephone,
								@NotBlank @Pattern(regexp = "\\d{11}") String cpf,
								@NotNull @Valid AddressData address) {

}
