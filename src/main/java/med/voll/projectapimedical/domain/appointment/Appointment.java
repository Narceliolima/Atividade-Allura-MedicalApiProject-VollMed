package med.voll.projectapimedical.domain.appointment;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.projectapimedical.domain.medic.Medic;
import med.voll.projectapimedical.domain.patient.Patient;

@Entity
@Table(name = "appointments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medic_id")
	private Medic medic;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@Column(name = "appointment_datetime")
	private LocalDateTime appointmentDateTime;
	
	@Column(name = "motive_of_cancelation")
	@Enumerated(EnumType.STRING)
	private MotiveOfCancelation motiveOfCancelation;

	public void cancel(@NotBlank MotiveOfCancelation motive) {
		
		this.motiveOfCancelation = motive;
	}
}
