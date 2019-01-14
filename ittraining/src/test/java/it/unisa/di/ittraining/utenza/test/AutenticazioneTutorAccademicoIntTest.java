package it.unisa.di.ittraining.utenza.test;

import static org.junit.Assert.assertEquals;

import it.unisa.di.ittraining.tutoraccademico.TutorAccademico;
import it.unisa.di.ittraining.tutoraccademico.TutorAccademicoService;
import it.unisa.di.ittraining.utente.AutenticazioneHolder;
import it.unisa.di.ittraining.utente.CognomeNonValidoException;
import it.unisa.di.ittraining.utente.DataDiNascitaNonValidaException;
import it.unisa.di.ittraining.utente.EmailEsistenteException;
import it.unisa.di.ittraining.utente.EmailNonValidaException;
import it.unisa.di.ittraining.utente.NomeCognomeTroppoCortoException;
import it.unisa.di.ittraining.utente.NomeCognomeTroppoLungoException;
import it.unisa.di.ittraining.utente.NomeNonValidoException;
import it.unisa.di.ittraining.utente.PasswordErrataException;
import it.unisa.di.ittraining.utente.PasswordNonCorrispondentiException;
import it.unisa.di.ittraining.utente.PasswordNonValidaException;
import it.unisa.di.ittraining.utente.SessoNonValidoException;
import it.unisa.di.ittraining.utente.TelefonoNonValidoException;
import it.unisa.di.ittraining.utente.UsernameEsistenteException;
import it.unisa.di.ittraining.utente.UsernameNonEsistenteException;
import it.unisa.di.ittraining.utente.UsernameNonValidoException;
import it.unisa.di.ittraining.utente.UtenteService;

import java.time.LocalDate;
import java.time.Month;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
public class AutenticazioneTutorAccademicoIntTest {

  @Autowired
  private TutorAccademicoService tutorService;

  @Autowired
  private UtenteService utentiService;
  
  @SuppressWarnings("static-access")
  @Test
  public void verificaLogin() {

    TutorAccademico tutor = new TutorAccademico();
    tutor.setUsername("Francesca");
    tutor.setNome("Franca");
    tutor.setCognome("Neri");
    tutor.setDataDiNascita(LocalDate.of(1970, Month.DECEMBER, 30));
    tutor.setTelefono("0987654324");
    tutor.setEmail("franca@unisa.it");
    tutor.setPassword("franca1");
    tutor.setSesso("F");
    
    try {
      tutor = tutorService.registraTutorAccademico(tutor);
    } catch (NomeNonValidoException | NomeCognomeTroppoLungoException
        | NomeCognomeTroppoCortoException
        | CognomeNonValidoException | EmailNonValidaException
        | EmailEsistenteException | TelefonoNonValidoException
        | DataDiNascitaNonValidaException | PasswordNonValidaException
        | PasswordNonCorrispondentiException
        | SessoNonValidoException | UsernameNonValidoException | UsernameEsistenteException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    try {
      utentiService.login(tutor.getUsername(), tutor.getPassword());
    } catch (UsernameNonEsistenteException | PasswordErrataException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    assertEquals(tutor.getUsername(), utentiService.getUtenteAutenticato().getUsername());
    
    utentiService.logout();
  }
}
