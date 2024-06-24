package med.voll.projectapimedical.domain.appointment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.projectapimedical.domain.medic.Speciality;

public record AppointmentScheduleData(Long idMedic,
										@NotNull Long idPatient,
										@NotNull @Future @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime appointmentDateTime,
										Speciality speciality) {
	
}
