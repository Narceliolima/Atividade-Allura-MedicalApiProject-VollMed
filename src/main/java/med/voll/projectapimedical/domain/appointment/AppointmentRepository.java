package med.voll.projectapimedical.domain.appointment;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

	Boolean existsByMedicIdAndAppointmentDateTimeAndMotiveOfCancelationIsNull(Long idMedic, LocalDateTime appointmentDateTime);

	Boolean existsByPatientIdAndAppointmentDateTimeBetween(Long idPatient, LocalDateTime firstTime,
			LocalDateTime lastTime);
}