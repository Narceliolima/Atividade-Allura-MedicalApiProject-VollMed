package med.voll.projectapimedical.domain.appointment.validation.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.projectapimedical.domain.appointment.AppointmentRepository;
import med.voll.projectapimedical.domain.appointment.AppointmentScheduleData;
import med.voll.projectapimedical.infra.exception.APIValidationException;

@Component
public class ValidateAnotherAppointmentMedicInSameTime implements ValidatorScheduleAppointment{

	@Autowired
	private AppointmentRepository repository;
	
	public void validate(AppointmentScheduleData data) {
		
		Boolean appointmentMedicInSameTime = repository.existsByMedicIdAndAppointmentDateTimeAndMotiveOfCancelationIsNull(data.idMedic(), data.appointmentDateTime());
		if(appointmentMedicInSameTime) {
			throw new APIValidationException("O Médico já possui outra consulta neste horário");
		}
	}
}
