package med.voll.projectapimedical.domain.medic;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.projectapimedical.domain.address.AddressData;
import med.voll.projectapimedical.domain.appointment.Appointment;
import med.voll.projectapimedical.domain.patient.Patient;
import med.voll.projectapimedical.domain.patient.PatientRegistryData;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class MedicRepositoryTest {

	@Autowired
	private MedicRepository repository;
	@Autowired
	private TestEntityManager em;
	
	@Test
	@DisplayName("Devolve null quando o médico solicitado não está disponivel na data")
	public void getRandomMedicAvailableCenary1() {
		
		//Given ou Arrange (Organizar/Inicializar) (Dado os seguintes parametros)
		LocalDateTime nextMondayAt10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
		var medic = cadastrarMedico("Antonio", "Antonio@medic.com", "123456", Speciality.CARDIOLOGIA);
		var patient = cadastrarPaciente("Jose", "Jose@teste.com", "12345678914");
		cadastrarConsulta(medic, patient, nextMondayAt10);
		
		//When ou Act (Executar) (Quando disparar uma ação)
		Medic availableMedic = repository.getRandomMedicAvailable(Speciality.CARDIOLOGIA, nextMondayAt10).orElse(null);
		
		//Then ou Assert (Conferir) (Esse é o resultado esperado)
		assertThat(availableMedic).isNull();
	}
	
	@Test
	@DisplayName("Devolve o médico quando está disponivel na data")
	public void getRandomMedicAvailableCenary2() {
		
		//Given ou Arrange (Organizar/Inicializar) (Dado os seguintes parametros)
		LocalDateTime nextMondayAt10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
		var medic = cadastrarMedico("Antonio", "Antonio@medic.com", "123456", Speciality.CARDIOLOGIA);
		
		//When ou Act (Executar) (Quando disparar uma ação)
		Medic availableMedic = repository.getRandomMedicAvailable(Speciality.CARDIOLOGIA, nextMondayAt10).orElse(null);
		
		//Then ou Assert (Conferir) (Esse é o resultado esperado)
		assertThat(availableMedic).isEqualTo(medic);
	}
	
	private void cadastrarConsulta(Medic medico, Patient paciente, LocalDateTime data) {
	    em.persist(new Appointment(null, medico, paciente, data, null));
	}

	private Medic cadastrarMedico(String nome, String email, String crm, Speciality especialidade) {
	    var medico = new Medic(dadosMedico(nome, email, crm, especialidade));
	    em.persist(medico);
	    return medico;
	}

	private Patient cadastrarPaciente(String nome, String email, String cpf) {
	    var paciente = new Patient(dadosPaciente(nome, email, cpf));
	    em.persist(paciente);
	    return paciente;
	}

	private MedicRegistryData dadosMedico(String nome, String email, String crm, Speciality especialidade) {
	    return new MedicRegistryData(
	            nome,
	            email,
	            "61999999999",
	            crm,
	            especialidade,
	            dadosEndereco()
	    );
	}

	private PatientRegistryData dadosPaciente(String nome, String email, String cpf) {
	    return new PatientRegistryData(
	            nome,
	            email,
	            "61999999999",
	            cpf,
	            dadosEndereco()
	    );
	}

	private AddressData dadosEndereco() {
	    return new AddressData(
	            "rua xpto",
	            "bairro",
	            "00000000",
	            "Brasilia",
	            "DF",
	            null,
	            null
	    );
	}
}
