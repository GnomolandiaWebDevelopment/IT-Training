package it.unisa.di.ittraining.registrazioneStudente.test;

import static org.junit.Assert.assertFalse;

import java.time.LocalDate;
import java.time.Month;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.unisa.di.ittraining.studente.Studente;
import it.unisa.di.ittraining.utente.PasswordNonValidaException;
import it.unisa.di.ittraining.utente.UtenteService;

/*
 * Classe di test per {@link elaboraRichiestaIscrizioneStudente in RegistrazioneController}
 * @author Alessia
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RegistrazioneStudentePasswordNonValidaTest {

	private Studente studente;
	@Autowired
	private UtenteService utenteService;
	
	@Before
	public void setUp() {
		//studente di test
		studente = new Studente();
		studente.setNome("Laura");
		studente.setUsername("Oliva");
		studente.setDataDiNascita(LocalDate.of(1997, Month.JUNE, 29));
		studente.setMatricola("0512100000");
		studente.setSesso("F");
		studente.setEmail("laura@studenti.unisa.it");
		studente.setPassword("abcd");
	}
	
	@Test
	public void testValidaPassword() throws PasswordNonValidaException {
		boolean flag = true;
		try {
			utenteService.validaPassword(studente.getPassword());
		}catch(Exception e){
			flag = false;
		}
		assertFalse(flag);
	}
	
	@After
	public void tearDown(){
	}
}