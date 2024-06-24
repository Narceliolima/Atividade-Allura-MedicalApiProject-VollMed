package med.voll.projectapimedical.domain.appointment;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import med.voll.projectapimedical.domain.appointment.validation.cancel.ValidatorCancelAppointment;
import med.voll.projectapimedical.domain.appointment.validation.schedule.ValidatorScheduleAppointment;
import med.voll.projectapimedical.domain.medic.Medic;
import med.voll.projectapimedical.domain.medic.MedicRepository;
import med.voll.projectapimedical.domain.patient.Patient;
import med.voll.projectapimedical.domain.patient.PatientRepository;
import med.voll.projectapimedical.infra.exception.APIValidationException;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private MedicRepository medicRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private List<ValidatorScheduleAppointment> scheduleValidators;
	@Autowired
	private List<ValidatorCancelAppointment> cancelValidators;
	
	
	public AppointmentDetailsData appointmentSchedule(@Valid AppointmentScheduleData data) {
		
		Optional<Medic> medic = data.idMedic()==null ? getRandomMedic(data) : medicRepository.findById(data.idMedic());
		Optional<Patient> patient = patientRepository.findById(data.idPatient());
		
		if(patient.isEmpty()) {
			throw new APIValidationException("Id do Paciente Informado não existe!");
		}
		if(medic.isEmpty()) {
			throw new APIValidationException("Id do Médico Informado não existe ou o Médico não está disponível nesta data");
		}

		scheduleValidators.forEach(validator -> validator.validate(data));
		
		Appointment appointment = new Appointment(null, medic.get(), patient.get(), data.appointmentDateTime(), null);
		appointmentRepository.save(appointment);
		
		return new AppointmentDetailsData(appointment);
	}

	public void appointmentCancel(@Valid AppointmentCancelData data) {

		Optional<Appointment> optionalAppointment = appointmentRepository.findById(data.idAppointment());
		
		if(optionalAppointment.isEmpty()) {
			throw new APIValidationException("Id da Consulta Informada não existe!");
		}

		cancelValidators.forEach(validator -> validator.validate(data));
		
		Appointment appointment = optionalAppointment.get();
		appointment.cancel(data.motiveOfCancelation());
		appointmentRepository.save(appointment);
	}
	
	private Optional<Medic> getRandomMedic(@Valid AppointmentScheduleData data) {
		
		if(data.speciality()==null) {
			throw new APIValidationException("Deve ser selecionado ao menos a especialidade");
		}
		
		return medicRepository.getRandomMedicAvailable(data.speciality(), data.appointmentDateTime());
	}
}
