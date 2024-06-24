package med.voll.projectapimedical.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import med.voll.projectapimedical.domain.address.AddressData;
import med.voll.projectapimedical.domain.medic.Medic;
import med.voll.projectapimedical.domain.medic.MedicDetailsData;
import med.voll.projectapimedical.domain.medic.MedicRegistryData;
import med.voll.projectapimedical.domain.medic.MedicRepository;
import med.voll.projectapimedical.domain.medic.Speciality;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class MedicControllerTest {
	
	@Autowired
	private MockMvc mvc;
	@Autowired
	private JacksonTester<MedicRegistryData> medicRegistryData;
	@Autowired
	private JacksonTester<MedicDetailsData> medicDetailsData;
	@MockBean
	private MedicRepository repository;

	@Test
	@WithMockUser
	@DisplayName("Devolver código 400 quando há algum parametro errado")
	public void registry_cenary1() throws Exception {
		
		MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/medicos")).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@WithMockUser
	@DisplayName("Devolver código 400 quando há algum parametro errado")
	public void registry_cenary2() throws Exception {
		
		String name = "Joao";
		String email = "Joao@hotmail.com";
		String telephone = "32130589";
		String crm = "123456";
		Speciality specility = Speciality.GINECOLOGIA;
		AddressData addressData = new AddressData("Caucaia", "Metropole", "65985075", "123", "Sem", "Fortal", "CE");
		MedicRegistryData medicRegistry = new MedicRegistryData(name, email, telephone, crm, specility, addressData);
		Medic medic = new Medic(medicRegistry);
		MedicDetailsData medicDetails = new MedicDetailsData(medic);

		when(repository.save(any())).thenReturn(medic);
		
		var response = mvc.perform(MockMvcRequestBuilders.post("/medicos").contentType(MediaType.APPLICATION_JSON)
									.content(medicRegistryData.write(medicRegistry)
											.getJson())).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		
		var expectedJson = medicDetailsData.write(medicDetails).getJson();
		
		assertThat(response.getContentAsString()).isEqualTo(expectedJson);
	}
}
