package it.unisa.di.ittraining.web;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
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

import it.unisa.di.ittraining.azienda.AziendaService;
import it.unisa.di.ittraining.azienda.TutorAziendale;
import it.unisa.di.ittraining.domandatirocinio.DomandaTirocinio;
import it.unisa.di.ittraining.domandatirocinio.DomandaTirocinioService;
import it.unisa.di.ittraining.studente.Studente;
import it.unisa.di.ittraining.utente.UtenteService;

@Controller
public class DomandaTirocinioController {
	
	@Autowired
	private AziendaService aziendeService;
	
	@Autowired
	private UtenteService utentiService;
	
	@Autowired
	private DomandaTirocinioService domandeService;
	
	@Autowired
	private DomandaTirocinioFormValidator validator;

	
	@RequestMapping(value = "/compila-domanda-form", method = RequestMethod.GET)
	public String showDomandaTirocinioForm(HttpServletRequest request, Model model) {
		

		if(utentiService.getUtenteAutenticato() == null || !(utentiService.getUtenteAutenticato().getClass().getSimpleName().equals("Studente")))
			return "not-available";
		
		if(!model.containsAttribute("domandaForm"))
			model.addAttribute("domandaForm", new DomandaTirocinioForm());
		
		if(request.getParameter("azienda") != null)
			model.addAttribute("nomeAzienda", request.getParameter("azienda"));
		
		
		return "compila-domanda";
	}
	
	@RequestMapping(value = "/domande-studente", method = RequestMethod.GET)
	public String elencaDomandeStudente(HttpSession session, Model model) {
		

		if(utentiService.getUtenteAutenticato() == null || !(utentiService.getUtenteAutenticato().getClass().getSimpleName().equals("Studente")))
			return "not-available";
		
		if(!model.containsAttribute("listaDomandeStudente"))
			model.addAttribute("listaDomandeStudente", domandeService.elencaDomandeStudente((String)session.getAttribute("username")));
		
		return "lista-domande-studente";
	}
	
	
	@RequestMapping(value = "/compila-domanda", method = RequestMethod.POST)
	public String elaboraDomandaTirocinio(@ModelAttribute("domandaForm") DomandaTirocinioForm domandaForm, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		validator.validate(domandaForm, result);
		
		if(result.hasErrors()) {
	      redirectAttributes
	          .addFlashAttribute("org.springframework.validation.BindingResult.domandaForm",
	                             result);
	      redirectAttributes.addFlashAttribute("domandaForm", domandaForm);
	      
	      return "compila-domanda";
			
		}
		
		DomandaTirocinio domanda = new DomandaTirocinio();
		
		// Genera un oggetto LocalDate a partire dagli interi presenti nel form
	    LocalDate inizio = LocalDate.of(domandaForm.getAnnoInizio(),
	    		domandaForm.getMeseInizio(),
	    		domandaForm.getGiornoInizio());
	    
	    // Genera un oggetto LocalDate a partire dagli interi presenti nel form
	    LocalDate fine = LocalDate.of(domandaForm.getAnnoFine(),
	    		domandaForm.getMeseFine(),
	    		domandaForm.getGiornoFine());
	    
	    Studente studente = (Studente)utentiService.getUtenteAutenticato();
	    
	    domanda.setData(LocalDate.now());
	    domanda.setInizioTirocinio(inizio);
	    domanda.setFineTirocinio(fine);
	    domanda.setCfu(domandaForm.getCfu());
	    domanda.setStatus(0);
	    domanda.setStudente(studente);
	    domanda.setAzienda(aziendeService.getAziendaByNome(domandaForm.getNomeAzienda()));
	    
	    if(domandaForm.getCfu() == 6)
	    	domanda.setOreTotali(150);
	    
	    else if(domandaForm.getCfu() == 12)
	    	domanda.setOreTotali(300);
	    
	    else if(domandaForm.getCfu() == 18)
	    	domanda.setOreTotali(450);
	    
	    
	    domandeService.registraDomanda(domanda);
		
		
		return "redirect:/home";
	}
	
	
	@RequestMapping(value = "/rifiuta-domanda", method = RequestMethod.GET)
	public String rifiutaDomanda(@RequestParam Long id) {
		
		DomandaTirocinio domanda = domandeService.getDomandaById(id);
		domanda.setStatus(DomandaTirocinio.RIFIUTATA_AZIENDA);
		
		domandeService.registraDomanda(domanda);
		
		return "redirect:/lista-domande-aziendale";
		
	}
	
	
	@RequestMapping(value = "/lista-domande-aziendale", method = RequestMethod.GET)
	public String elencaDomandeAziendali(Model model) {
		

		if(utentiService.getUtenteAutenticato() == null || !(utentiService.getUtenteAutenticato().getClass().getSimpleName().equals("TutorAziendale")))
			return "not-available";
		
		if(!model.containsAttribute("listaDomandeAzienda")) {
			TutorAziendale tutor = (TutorAziendale)utentiService.getUtenteAutenticato();
			
			model.addAttribute("listaDomandeAzienda", domandeService.elencaDomandeAziendali(tutor.getAzienda()));
			
		}
		
		if(!model.containsAttribute("progettoForm"))
			model.addAttribute("progettoForm", new ProgettoFormativoForm());
		
		return "lista-domande-aziendale";
	}
}
