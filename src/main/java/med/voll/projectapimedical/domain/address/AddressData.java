package med.voll.projectapimedical.domain.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressData(@NotBlank String logradouro,
							@NotBlank String neighborhood,
							@NotBlank @Pattern(regexp = "\\d{8}") String cep,
							@NotBlank String city,
							@NotBlank String uf,
							String complement,
							String number) {

}
