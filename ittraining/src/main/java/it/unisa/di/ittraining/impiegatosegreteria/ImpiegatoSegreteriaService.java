package it.unisa.di.ittraining.impiegatosegreteria;

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
import it.unisa.di.ittraining.utente.TelefonoNonValidoException;
import it.unisa.di.ittraining.utente.UsernameEsistenteException;
import it.unisa.di.ittraining.utente.UsernameNonValidoException;
import it.unisa.di.ittraining.utente.Utente;
import it.unisa.di.ittraining.utente.UtenteRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImpiegatoSegreteriaService {

  @Autowired
  
  private ImpiegatoSegreteriaRepository rep;

  @Autowired
  
  private UtenteRepository utenteRep;

  /** 
  * Costante che rappresenta la minima distanza in anni dalla data corrente 
  * per la data di nascita.
  */
  public static final int MIN_DISTANZA_ANNO_NASCITA = 17;

  /** 
  * Costante che rappresenta la massima distanza in anni dalla data corrente 
  * per la data di nascita.
  */
  public static final int MAX_DISTANZA_ANNO_NASCITA = 130;

  /** 
  * Metodo che permette di validare lato server i campi inseriti dall'impiegato
  * e inserire lo stesso nel Database.
  */
  @Transactional(rollbackFor = Exception.class)
  public ImpiegatoSegreteria registraImpiegato(ImpiegatoSegreteria impiegato) 
      throws NomeNonValidoException, NomeCognomeTroppoLungoException, 
      NomeCognomeTroppoCortoException,
      CognomeNonValidoException, EmailNonValidaException, 
      EmailEsistenteException, PasswordNonValidaException, 
      PasswordNonCorrispondentiException, DataDiNascitaNonValidaException,
      UsernameNonValidoException, UsernameEsistenteException, 
      SessoNonValidoException, TelefonoNonValidoException {

    impiegato.setNome(validaNome(impiegato.getNome()));
    impiegato.setCognome(validaCognome(impiegato.getCognome()));
    impiegato.setEmail(validaEmailImpiegato(impiegato.getEmail()));
    impiegato.setPassword(validaPassword(impiegato.getPassword()));
    impiegato.setDataDiNascita(validaDataDiNascita(impiegato.getDataDiNascita()));
    impiegato.setUsername(validaUsername(impiegato.getUsername()));
    impiegato.setSesso(validaSesso(impiegato.getSesso()));
    impiegato.setTelefono(validaTelefono(impiegato.getTelefono()));

    impiegato = rep.save(impiegato);

    return impiegato;

  }
  
  /** 
  * Metodo che permette di validare il campo email.
  */
  public String validaEmailImpiegato(String email) throws EmailNonValidaException, 
      EmailEsistenteException {

    if (email == null) {
      throw new EmailNonValidaException("Il campo email non può essere nullo");
    }

    if (!email.matches(ImpiegatoSegreteria.EMAIL_PATTERN_SEGRETERIA)) {
      throw new EmailNonValidaException("Il campo email non rispetta il formato indicato");
    }

    if (rep.existsByEmail(email)) {
      throw new EmailEsistenteException("L'email è già utilizzata da un altro impiegato");
    }

    return email;
  }

  /** 
  * Metodo che permette di validare il campo nome.
  */
  public String validaNome(String nome) throws NomeNonValidoException,
      NomeCognomeTroppoLungoException, NomeCognomeTroppoCortoException {

    if (nome == null) {
      throw new NomeNonValidoException();
    }

    if (nome.length() > Utente.MAX_LUNGHEZZA_NOME) {
      throw new NomeCognomeTroppoLungoException();
    }

    if (nome.length() < Utente.MIN_LUNGHEZZA_NOME) {
      throw new NomeCognomeTroppoCortoException();
    }

    if (!nome.matches(Utente.NOME_PATTERN)) {
      throw new NomeNonValidoException("Il campo nome non rispetta il formato indicato");
    }

    return nome;
  }


  /** 
  * Metodo che permette di validare il campo cognome.
  */
  public String validaCognome(String cognome) throws CognomeNonValidoException,
      NomeCognomeTroppoLungoException, NomeCognomeTroppoCortoException {

    if (cognome == null) {
      throw new CognomeNonValidoException();
    }

    if (cognome.length() > Utente.MAX_LUNGHEZZA_NOME) {
      throw new NomeCognomeTroppoLungoException();
    }

    if (cognome.length() < Utente.MIN_LUNGHEZZA_NOME) {
      throw new NomeCognomeTroppoCortoException();
    }

    if (!cognome.matches(Utente.COGNOME_PATTERN)) {
      throw new CognomeNonValidoException("Il cognome non rispetta il formato indicato");
    }

    return cognome;
  }


  /** 
  * Metodo che permette di validare il campo sesso.
  */
  public String validaSesso(String sesso) throws SessoNonValidoException {

    if (sesso == null) {

      throw new SessoNonValidoException();

    } else {

      sesso = sesso.trim();

      if (!sesso.equals(Utente.SESSO_MASCHILE) && !sesso.equals(Utente.SESSO_FEMMINILE)) {

        throw new SessoNonValidoException();

      } else {

        return sesso;
      }
    }
  }


  /** 
  * Metodo che permette di validare il campo data di nascita.
  */
  public LocalDate validaDataDiNascita(LocalDate dataDiNascita) 
      throws DataDiNascitaNonValidaException {

    if (dataDiNascita == null) {
      throw new DataDiNascitaNonValidaException();

    } else {

      LocalDate oggi = LocalDate.now();
      long distanza = ChronoUnit.YEARS.between(dataDiNascita, oggi);

      if (distanza < MIN_DISTANZA_ANNO_NASCITA || distanza > MAX_DISTANZA_ANNO_NASCITA) {

        throw new DataDiNascitaNonValidaException();

      } else {

        return dataDiNascita;
      } 
    }
  }
  
  /** 
  * Metodo che permette di validare il campo telefono.
  */
  public String validaTelefono(String telefono) throws TelefonoNonValidoException {

    if (telefono == null) {
      throw new TelefonoNonValidoException("Il campo telefono non può essere nullo");
    }

    if (!telefono.matches(Utente.TELEFONO_PATTERN)) {
      throw new TelefonoNonValidoException("Il campo telefono non è valido");
    }

    return telefono;
    
  }

  /** 
  * Metodo che permette di validare il campo username.
  */
  public String validaUsername(String username) throws UsernameNonValidoException, 
      UsernameEsistenteException {

    if (username == null) {

      throw new UsernameNonValidoException("Il campo username è nullo");

    } else {

      username = username.trim();
    }

    if (!username.matches(Utente.USERNAME_PATTERN)) {
      throw new UsernameNonValidoException("Lo username non rispetta il formato.");
      
    } else if (utenteRep.existsByUsername(username)) {

      throw new UsernameEsistenteException("Username già utilizzato.");
    }

    return username;

  }

  /** 
  * Metodo che permette di validare la corrispodenza tra i campi password
  * e conferma password.
  */
  public boolean validaPasswords(String password, String confirmPassword)
      throws PasswordNonValidaException,
      PasswordNonCorrispondentiException {

    if (password == null || confirmPassword == null) {
      throw 
        new
          PasswordNonValidaException("Il campo password oppure conferma password sono nulli");
    }

    if (!password.matches(Utente.PASSWORD_PATTERN)
        || !confirmPassword.matches(Utente.PASSWORD_PATTERN)) {
      throw
        new
          PasswordNonValidaException("I campi passwords non rispettano il formato previsto");
    }

    if (!password.equals(confirmPassword)) {
      throw new PasswordNonCorrispondentiException("Le due password non corrispondono");
    }

    return true;
  }

  /** 
  * Metodo che permette di validare il campo password.
  */
  public String validaPassword(String password) throws PasswordNonValidaException,
      PasswordNonCorrispondentiException {

    if (password == null) {
      throw new PasswordNonValidaException("IMPIEGATO SERVICE - Il campo password non è valido");
    }

    if (!password.matches(Utente.PASSWORD_PATTERN)) {
      throw new PasswordNonValidaException("IMPIEGATO SERVICE - Non rispecchia il formato");
    }

    return password;

  }
}
