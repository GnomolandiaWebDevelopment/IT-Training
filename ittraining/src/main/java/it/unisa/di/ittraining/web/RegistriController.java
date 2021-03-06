package it.unisa.di.ittraining.web;

import it.unisa.di.ittraining.domandatirocinio.DomandaTirocinio;
import it.unisa.di.ittraining.domandatirocinio.DomandaTirocinioService;
import it.unisa.di.ittraining.registrotirocinio.DataRegistroNonValidaException;
import it.unisa.di.ittraining.registrotirocinio.DataRegistroPrecedenteInizioException;
import it.unisa.di.ittraining.registrotirocinio.DataRegistroSuccessivaFineException;
import it.unisa.di.ittraining.registrotirocinio.MassimoNumeroOreException;
import it.unisa.di.ittraining.registrotirocinio.OrarioFinePrecedenteInizioException;
import it.unisa.di.ittraining.registrotirocinio.OrarioNonValidoException;
import it.unisa.di.ittraining.registrotirocinio.OrePrevisteSuperateException;
import it.unisa.di.ittraining.registrotirocinio.Registro;
import it.unisa.di.ittraining.registrotirocinio.RegistroService;
import it.unisa.di.ittraining.utente.UtenteService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistriController {

  @Autowired
  private RegistroService registriService;

  @Autowired
  private DomandaTirocinioService domandeService;

  @Autowired
  private RegistroFormValidator validator;

  @Autowired
  private UtenteService utentiService;

  /**
  * Permette allo studente di visualizzare la lista delle domande delle quali
  * è possibile compilare il registro.
  */
  @RequestMapping(value = "/home/domande-registri", method = RequestMethod.GET)
  public String showRegistri(HttpSession session, Model model) {

    if (utentiService.getUtenteAutenticato() == null 
         || !(utentiService.getUtenteAutenticato().getClass().getSimpleName().equals("Studente"))) {
      return "not-available";
    }
    
    if (!model.containsAttribute("listaDomandeApprovate")) {
      List<DomandaTirocinio> domande = domandeService
              .elencaDomandeStudenteStatus((String)session.getAttribute("username"), 
              DomandaTirocinio.APPROVATA);
      model.addAttribute("listaDomandeApprovate", domande);

      model.addAttribute("registroForm", new RegistroForm());
    }

    return "registri-domande";
  }

  /**
  * Permette di visualizzare il form per compilare il registro.
  */
  @RequestMapping(value = "/home/registro-form", method = RequestMethod.GET)
  public String showRegistroForm(@RequestParam Long id, Model model) {
    if (utentiService.getUtenteAutenticato() == null 
         || !(utentiService.getUtenteAutenticato().getClass().getSimpleName().equals("Studente"))) {
      return "not-available";
    }

    DomandaTirocinio domanda = domandeService.getDomandaById(id);

    model.addAttribute("domanda", domanda);

    if (!model.containsAttribute("registroForm")) {
      model.addAttribute("registroForm", new RegistroForm());
    }
    
    return "compila-registro";
  }

  /**
  * Permette di elaborare i dati inseriti nel form, validarli e, se non ci sono errori, aggiungere
  * una nuova attività all'interno del Database.
  */
  @RequestMapping(value = "/home/registro-form/compila-registro", method = RequestMethod.POST)
  public String compilaRegistro(@ModelAttribute("registroForm") RegistroForm registroForm, 
      BindingResult result, Model model, RedirectAttributes redirectAttributes, HttpSession session)
      throws DataRegistroSuccessivaFineException, DataRegistroPrecedenteInizioException, 
      DataRegistroNonValidaException, OrarioNonValidoException, 
      OrarioFinePrecedenteInizioException, MassimoNumeroOreException, OrePrevisteSuperateException {

    validator.validate(registroForm, result);

    if (result.hasErrors()) {
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.registroForm",
             result);
      redirectAttributes.addFlashAttribute("registroForm", registroForm);
      redirectAttributes.addFlashAttribute("testoNotifica", "toast.registro.nonValido");

      return "redirect:/home/registro-form?id=" + registroForm.getIdDomanda();
    }

    Registro registro = new Registro();

    LocalDate data = LocalDate.of(registroForm.getAnno(), registroForm.getMese(), 
        registroForm.getGiorno());
    LocalTime inizio = LocalTime.of(registroForm.getOraInizio(), registroForm.getMinutoInizio());
    LocalTime fine = LocalTime.of(registroForm.getOraFine(), registroForm.getMinutoFine());

    registro.setData(data);
    registro.setDescrizione(registroForm.getDescrizione());
    registro.setInizio(inizio);
    registro.setFine(fine);
    registro.setNumero_minuti(((ChronoUnit.MILLIS.between(inizio, fine) / 1000) / 60));
    registriService.registraTirocinio(registro, registroForm.getIdDomanda());

    redirectAttributes.addFlashAttribute("testoNotifica", "toast.registro.valido");

    return "redirect:/home/registro-form?id=" + registroForm.getIdDomanda();
  }

  /**
  * Permette di eliminare un'attività dal registro dello studente.
  */
  @RequestMapping(value = "/home/registro-form/cancella-tirocinio", method = RequestMethod.GET)
  public String cancellaTirocinio(@RequestParam Long id, @RequestParam Long idDomanda,
      RedirectAttributes redirectAttributes) {

    registriService.cancellaTirocinio(id);
    
    redirectAttributes.addFlashAttribute("testoNotifica", "toast.registro.eliminatirocinio");
    
    return "redirect:/home/registro-form?id=" + idDomanda;
  }

  /**
  * Permette al tutor aziendale di visualizzare i registri degli studenti
  * che stanno svolgendo l'attività di tirocnio presso l'azienda a cui appartiene.
  */
  @RequestMapping(value = "/home/registri-aziendale", method = RequestMethod.GET)
  public String showRegistriAziendale(Model model) {

    if (utentiService.getUtenteAutenticato() == null 
         || !(utentiService.getUtenteAutenticato().getClass().getSimpleName()
            .equals("TutorAziendale"))) {
      return "not-available";
    }
    if (!model.containsAttribute("listaDomandeRegistri")) {
      List<DomandaTirocinio> domande = domandeService.getAllRegistriAzienda();
      
      model.addAttribute("listaDomandeRegistri", domande);
    }
    return "registri-aziendale";
  }

  /**
  * Permette al tutor accademico di visualizzare i registri approvati dai tutor aziendali.
  */
  @RequestMapping(value = "/home/registri-accademico", method = RequestMethod.GET)
  public String showRegistriAccademico(Model model) {

    if (utentiService.getUtenteAutenticato() == null 
        || !(utentiService.getUtenteAutenticato().getClass().getSimpleName()
           .equals("TutorAccademico"))) {
      return "not-available";
    }

    if (!model.containsAttribute("listaDomandeRegistri")) {
      List<DomandaTirocinio> domande = domandeService.getAllRegistriAccademico();
      model.addAttribute("listaDomandeRegistri", domande);
    }

    return "registri-accademico";
  }

  /**
  * Permette all'impiegato di segreteria di visualizzare i registri approvati
  * dai tutor accademici.
  */
  @RequestMapping(value = "/home/registri-segreteria", method = RequestMethod.GET)
  public String showRegistriSegreteria(Model model) {

    if (utentiService.getUtenteAutenticato() == null 
        || !(utentiService.getUtenteAutenticato().getClass().getSimpleName()
        .equals("ImpiegatoSegreteria"))) {
      return "not-available";
    }

    if (!model.containsAttribute("listaDomandeRegistri")) {
      List<DomandaTirocinio> domande = domandeService.getAllRegistriSegreteria();

      model.addAttribute("listaDomandeRegistri", domande);
    }

    return "registri-segreteria";
  }

  /**
  * Permette ai tutor aziendali, ai tutor accademici e all'impiegato di segreteria
  * di approvare i registri degli studenti.
  */
  @RequestMapping(value = "/home/registri/approva-registro", method = RequestMethod.GET)
  public String approvaRegistro(@RequestParam Long id, RedirectAttributes redirectAttributes) {

    if (utentiService.getUtenteAutenticato() == null) {
      return "not-available";
    }

    if (utentiService.getUtenteAutenticato().getClass().getSimpleName().equals("TutorAziendale")) {
      domandeService.aggiornaStatoDomanda(id, DomandaTirocinio.REGISTRO_APPROVATO_AZIENDALE);

      redirectAttributes.addFlashAttribute("testoNotifica", "toast.registro.approvato");

      return "redirect:/home/registri-aziendale";
    }

    if (utentiService.getUtenteAutenticato().getClass().getSimpleName().equals("TutorAccademico")) {

      domandeService.aggiornaStatoDomanda(id, DomandaTirocinio.REGISTRO_APPROVATO_ACCADEMICO);

      redirectAttributes.addFlashAttribute("testoNotifica", "toast.registro.approvato");

      return "redirect:/home/registri-accademico";

    }

    if (utentiService.getUtenteAutenticato().getClass().getSimpleName()
        .equals("ImpiegatoSegreteria")) {
      domandeService.aggiornaStatoDomanda(id, DomandaTirocinio.REGISTRO_APPROVATO_SEGRETERIA);

      redirectAttributes.addFlashAttribute("testoNotifica", "toast.registro.approvato");

      return "redirect:/home/registri-segreteria";
    }

    redirectAttributes.addFlashAttribute("testoNotifica", "toast.registro.warning");

    return "redirect:/home";
  }
}
