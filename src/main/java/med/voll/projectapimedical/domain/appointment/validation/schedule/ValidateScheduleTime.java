package med.voll.projectapimedical.domain.appointment.validation.schedule;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.projectapimedical.domain.appointment.AppointmentScheduleData;
import med.voll.projectapimedical.infra.exception.APIValidationException;

@Component
public class ValidateScheduleTime implements ValidatorScheduleAppointment{

	public void validate(AppointmentScheduleData data) {
		
		LocalDateTime appointmentDateTime = data.appointmentDateTime();
		LocalDateTime now = LocalDateTime.now();
		Long minutesDiference = Duration.between(now, appointmentDateTime).toMinutes();
		
		if(minutesDiference<30) {
			throw new APIValidationException("Consulta deve ser agendada com antecedencia minima de 30 minutos.");
		}
	}
}
