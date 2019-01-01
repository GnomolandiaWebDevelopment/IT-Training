package it.unisa.di.ittraining.tutoraccademico;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import it.unisa.di.ittraining.domandatirocinio.DomandaTirocinio;
import it.unisa.di.ittraining.registrotirocinio.Registro;
import it.unisa.di.ittraining.studente.Studente;
import it.unisa.di.ittraining.utente.Utente;

@Entity
public class TutorAccademico extends Utente {
	
	@OneToMany
	private List<DomandaTirocinio> domande;
	  
	/** Espressione regolare che definisce il formato del campo email per impiegati di segreteria e tutor accademici. */
	public static final String EMAIL_PATTERN_ACCADEMICO = "[A-z0-9\\.\\+_-]+@unisa.it";
	
	@OneToMany
	private List<Registro> registri;
	
	@OneToMany
	private List<Studente> studenti;
	
	public TutorAccademico() {
		
	}

	public List<DomandaTirocinio> getDomande() {
		return domande;
	}

	public void setDomande(List<DomandaTirocinio> domande) {
		this.domande = domande;
	}

	public List<Registro> getRegistri() {
		return registri;
	}

	public void setRegistri(List<Registro> registri) {
		this.registri = registri;
	}

	public List<Studente> getStudenti() {
		return studenti;
	}

	public void setStudenti(List<Studente> studenti) {
		this.studenti = studenti;
	}
	
	
}
