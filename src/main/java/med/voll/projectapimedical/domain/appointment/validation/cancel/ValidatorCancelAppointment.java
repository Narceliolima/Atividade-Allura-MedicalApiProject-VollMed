package med.voll.projectapimedical.domain.appointment.validation.cancel;

import med.voll.projectapimedical.domain.appointment.AppointmentCancelData;

public interface ValidatorCancelAppointment {

	public void validate(AppointmentCancelData data);
}
