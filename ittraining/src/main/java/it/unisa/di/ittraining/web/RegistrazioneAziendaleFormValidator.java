package it.unisa.di.ittraining.web;

import it.unisa.di.ittraining.azienda.AziendaNonEsistenteException;
import it.unisa.di.ittraining.azienda.AziendaNonValidaException;
import it.unisa.di.ittraining.azienda.AziendaService;
import it.unisa.di.ittraining.azienda.EmailNonAssociataException;
import it.unisa.di.ittraining.utente.CognomeNonValidoException;
import it.unisa.di.ittraining.utente.DataDiNascitaNonValidaException;
import it.unisa.di.ittraining.utente.EmailEsistenteException;
import it.unisa.di.ittraining.utente.EmailNonValidaException;
import it.unisa.di.ittraining.utente.NomeCognomeTroppoCortoException;
import it.unisa.di.ittraining.utente.NomeCognomeTroppoLungoException;
import it.unisa.di.ittraining.utente.NomeNonValidoException;
import it.unisa.di.ittraining.utente.PasswordNonCorrispondentiException;
import it.unisa.di.ittraining.utente.PasswordNonValidaException;
import it.unisa.di.ittraining.utente.SessoNonValidoException;
import it.unisa.di.ittraining.utente.UsernameEsistenteException;
import it.unisa.di.ittraining.utente.UsernameNonValidoException;
import java.time.DateTimeException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrazioneAziendaleFormValidator implements Validator {

  @Autowired
  private AziendaService aziendeService;

  /**
  * Permette di definire le classi cui il validatore è applicabile.
  */
  @Override
  public boolean supports(Class<?> clazz) {
    return RegistrazioneAziendaleForm.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    RegistrazioneAziendaleForm form = (RegistrazioneAziendaleForm) target;
    try {
      aziendeService.validaNomeTutor(form.getNome());
    } catch (NomeNonValidoException e) {
      // TODO Auto-generated catch block
      errors.rejectValue("nome", "formRegistrazione.nome.nonValido");
    } catch (NomeCognomeTroppoLungoException e) {
      errors.rejectValue("nome", "formRegistrazione.nome.lungo");
      e.printStackTrace();
    } catch (NomeCognomeTroppoCortoException e) {
      // TODO Auto-generated catch block
      errors.rejectValue("nome", "formRegistrazione.nome.corto");
    }
    
    try {
      aziendeService.validaCognome(form.getCognome());
    } catch (CognomeNonValidoException e) {
      // TODO Auto-generated catch block
      errors.rejectValue("cognome", "formRegistrazione.cognome.nonValido");
    } catch (NomeCognomeTroppoLungoException e) {
      // TODO Auto-generated catch block
      errors.rejectValue("cognome", "formRegistrazione.cognome.lungo");
    } catch (NomeCognomeTroppoCortoException e) {
      // TODO Auto-generated catch block
      errors.rejectValue("cognome", "formRegistrazione.cognome.corto");
    }

    try {
      aziendeService.validaTelefono(form.getTelefono());
    } catch (it.unisa.di.ittraining.azienda.TelefonoNonValidoException e2) {
      // TODO Auto-generated catch block
      errors.rejectValue("telefono", "formRegistrazione.telefono.nonValido");
    }

    try {
      aziendeService.validaUsername(form.getUsername());
    } catch (UsernameNonValidoException e) {
      // TODO Auto-generated catch block
      errors.rejectValue("username", "formRegistrazione.username.nonValido");
    } catch (UsernameEsistenteException e) {
      // TODO Auto-generated catch block
      errors.rejectValue("username", "formRegistrazione.username.usernameEsistente");
    }

    try {
      aziendeService.validaPasswords(form.getPassword(), form.getConfermaPassword());
    } catch (PasswordNonValidaException e) {
      // TODO Auto-generated catch block
      errors.rejectValue("password", "formRegistrazione.password.passwordsNonValide");
    } catch (PasswordNonCorrispondentiException e) {
      // TODO Auto-generated catch block 
      errors.rejectValue("password", "formRegistrazione.confermaPassword.nonCorrispondenti");
    }

    try {
      aziendeService.validaSesso(form.getSesso());
    } catch (SessoNonValidoException e) {
      // TODO Auto-generated catch block
      errors.rejectValue("sesso", "formRegistrazione.sesso.nonValido");
    }

    try {
      aziendeService.validaNomeForTutor(form.getNomeAzienda());
    } catch (AziendaNonValidaException e1) {
      // TODO Auto-generated catch block
      errors.rejectValue("nomeAzienda", "formConvenzione.nome.nonValido");
    } catch (AziendaNonEsistenteException e) {
      // TODO Auto-generated catch block
      errors.rejectValue("nomeAzienda", "formConvenzione.nome.nonEsistente");
    }

    try {
      aziendeService.validaEmailTutor(form.getNomeAzienda(), form.getEmail());
    } catch (EmailNonValidaException e1) {
      // TODO Auto-generated catch block
      errors.rejectValue("email", "formConvenzione.email.nonValida");
    } catch (EmailEsistenteException e1) {
      // TODO Auto-generated catch block
      errors.rejectValue("email", "formConvenzione.email.esistente");
    } catch (EmailNonAssociataException e1) {
      // TODO Auto-generated catch block
      errors.rejectValue("email", "formConvenzione.email.nonAssociata");
    }

    try {
      if (form.getAnnoNascita() == null
          || form.getMeseNascita() == null
          || form.getGiornoNascita() == null) {
        throw new DataDiNascitaNonValidaException();
      }
      
      LocalDate data = LocalDate.of(form.getAnnoNascita(),
          form.getMeseNascita(), 
          form.getGiornoNascita());

      aziendeService.validaDataDiNascita(data);
    } catch (DataDiNascitaNonValidaException | DateTimeException e) {
      errors.rejectValue("giornoNascita", "formRegistrazione.data.nonValida");
    }
  }
}
