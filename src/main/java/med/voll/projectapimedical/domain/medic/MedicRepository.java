package med.voll.projectapimedical.domain.medic;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

public interface MedicRepository extends JpaRepository<Medic, Long>{

	Page<Medic> findAllByIsActiveTrue(Pageable pageable);

	@Query("SELECT medic FROM Medic medic WHERE medic.isActive = true AND medic.speciality = :speciality AND medic.id not in("
			+ "SELECT appointment.medic.id FROM Appointment appointment WHERE appointment.appointmentDateTime = :appointmentDateTime AND appointment.motiveOfCancelation IS NULL)"
			+ "ORDER BY RAND() LIMIT 1")
	Optional<Medic> getRandomMedicAvailable(Speciality speciality, @NotBlank @Future LocalDateTime appointmentDateTime);
}
