package med.voll.projectapimedical.domain.appointment.validation.schedule;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.projectapimedical.domain.appointment.AppointmentScheduleData;
import med.voll.projectapimedical.infra.exception.APIValidationException;

@Component
public class ValidateClinicOpeningHours implements ValidatorScheduleAppointment{

	public void validate(AppointmentScheduleData data) {

		LocalDateTime appointmentDateTime = data.appointmentDateTime();
		
		Boolean sunday = appointmentDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY);
		Boolean beforeClinicOpening = appointmentDateTime.getHour() < 7;
		Boolean afterClinicOpening = appointmentDateTime.getHour() > 18;
		
		if(sunday||beforeClinicOpening||afterClinicOpening) {
			throw new APIValidationException("Consulta fora do horário de funcionamento da clinica");
		}
	}
}
