package med.voll.projectapimedical.domain.appointment;

import java.time.LocalDateTime;

public record AppointmentDetailsData(Long id,
									Long idMedic,
									Long idPatient,
									LocalDateTime appointmentDateTime) {

	public AppointmentDetailsData(Appointment appointment) {
		this(appointment.getId(), appointment.getMedic().getId(), appointment.getPatient().getId(), appointment.getAppointmentDateTime());
	}

}
