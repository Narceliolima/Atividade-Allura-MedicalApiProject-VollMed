package med.voll.projectapimedical.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import med.voll.projectapimedical.domain.appointment.AppointmentDetailsData;
import med.voll.projectapimedical.domain.appointment.AppointmentScheduleData;
import med.voll.projectapimedical.domain.appointment.AppointmentService;
import med.voll.projectapimedical.domain.medic.Speciality;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class AppointmentControllerTest {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private JacksonTester<AppointmentScheduleData> appointSheduleData;
	@Autowired
	private JacksonTester<AppointmentDetailsData> appointDetailsData;
	@MockBean
	private AppointmentService service;
	
	@Test
	@WithMockUser
	@DisplayName("Devolver o código HTTP 400 quando as informações estão invalidas")
	public void schedule_scenary1() throws Exception {
		
		var response = mvc.perform(MockMvcRequestBuilders.post("/consultas")).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@WithMockUser
	@DisplayName("Devolver o código HTTP 200 quando as informações estão validas")
	public void schedule_scenary2() throws Exception {
		
		LocalDateTime date = LocalDateTime.now().plusHours(1);
		Speciality speciality = Speciality.CARDIOLOGIA;
		AppointmentDetailsData appointDetail = new AppointmentDetailsData(null, 1l, 1l, date);
		
		when(service.appointmentSchedule(any())).thenReturn(appointDetail);
		
		var response = mvc.perform(MockMvcRequestBuilders.post("/consultas")
									.contentType(MediaType.APPLICATION_JSON)
									.content(appointSheduleData.write(new AppointmentScheduleData(1l, 2l, date, speciality))
											.getJson()))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		var expectedJson = appointDetailsData.write(appointDetail).getJson();
		
		assertThat(response.getContentAsString()).isEqualTo(expectedJson);
	}
}
