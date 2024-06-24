package med.voll.projectapimedical.domain.appointment.validation.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.projectapimedical.domain.appointment.AppointmentScheduleData;
import med.voll.projectapimedical.domain.patient.PatientRepository;
import med.voll.projectapimedical.infra.exception.APIValidationException;

@Component
public class ValidateActivePatient implements ValidatorScheduleAppointment{

	@Autowired
	private PatientRepository repository;

	public void validate(AppointmentScheduleData data) {
		
		if(data.idPatient()==null) {
			return;
		}
		
		Boolean patientIsActive = repository.getReferenceById(data.idPatient()).getIsActive();
		if(!patientIsActive) {
			throw new APIValidationException("Paciente selecionado não está mais ativo");
		}
	}
}
