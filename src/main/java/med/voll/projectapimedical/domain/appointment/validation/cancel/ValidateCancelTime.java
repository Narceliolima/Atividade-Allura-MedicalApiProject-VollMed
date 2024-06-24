package med.voll.projectapimedical.domain.appointment.validation.cancel;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.projectapimedical.domain.appointment.Appointment;
import med.voll.projectapimedical.domain.appointment.AppointmentCancelData;
import med.voll.projectapimedical.domain.appointment.AppointmentRepository;
import med.voll.projectapimedical.infra.exception.APIValidationException;

@Component //É possivel nomear componentes para diferenciar dentro do spring
public class ValidateCancelTime implements ValidatorCancelAppointment{
	
	@Autowired
	private AppointmentRepository repository;

	public void validate(AppointmentCancelData data) {
		
		Appointment appointment = repository.getReferenceById(data.idAppointment());
		LocalDateTime now = LocalDateTime.now();
		Long hoursDiference = Duration.between(now, appointment.getAppointmentDateTime()).toHours();
		
		if(hoursDiference<24) {
			throw new APIValidationException("Consulta só pode ser cancelada com antecedencia minima de 24 horas.");
		}
	}
}
