package med.voll.projectapimedical.domain.appointment;

import jakarta.validation.constraints.NotNull;

public record AppointmentCancelData(@NotNull Long idAppointment,
									@NotNull MotiveOfCancelation motiveOfCancelation) {

}
