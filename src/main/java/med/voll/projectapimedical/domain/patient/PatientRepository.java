package med.voll.projectapimedical.domain.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long>{

	Page<Patient> findAllByisActiveTrue(Pageable pageable);
}
