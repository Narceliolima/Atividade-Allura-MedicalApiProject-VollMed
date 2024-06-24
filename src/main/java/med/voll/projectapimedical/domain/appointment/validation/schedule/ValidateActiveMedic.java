package med.voll.projectapimedical.domain.appointment.validation.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.projectapimedical.domain.appointment.AppointmentScheduleData;
import med.voll.projectapimedical.domain.medic.MedicRepository;
import med.voll.projectapimedical.infra.exception.APIValidationException;

@Component
public class ValidateActiveMedic implements ValidatorScheduleAppointment{

	@Autowired
	private MedicRepository repository;

	public void validate(AppointmentScheduleData data) {
		
		if(data.idMedic()==null) {
			return;
		}
		
		Boolean medicIsActive = repository.getReferenceById(data.idMedic()).getIsActive();
		if(!medicIsActive) {
			throw new APIValidationException("Médico selecionado não está mais ativo");
		}
	}
}
