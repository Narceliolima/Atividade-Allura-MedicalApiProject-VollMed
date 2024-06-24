package med.voll.projectapimedical.domain.appointment.validation.schedule;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.projectapimedical.domain.appointment.AppointmentRepository;
import med.voll.projectapimedical.domain.appointment.AppointmentScheduleData;
import med.voll.projectapimedical.infra.exception.APIValidationException;

@Component
public class ValidateAnotherPatientAppointmentInSameDay implements ValidatorScheduleAppointment{

	@Autowired
	private AppointmentRepository repository;
	
	public void validate(AppointmentScheduleData data) {
		
		LocalDateTime firstTime = data.appointmentDateTime().withHour(7);
		LocalDateTime lastTime = data.appointmentDateTime().withHour(18);
		Boolean patientAppointmentInSameDay = repository.existsByPatientIdAndAppointmentDateTimeBetween(data.idPatient(), firstTime, lastTime);
		if(patientAppointmentInSameDay) {
			throw new APIValidationException("Paciente já possui consulta agendada neste dia");
		}
	}
}
