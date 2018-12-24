package it.unisa.di.ittraining.azienda;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import it.unisa.di.ittraining.progettoformativo.ProgettoFormativo;
import it.unisa.di.ittraining.registrotirocinio.Registro;
import it.unisa.di.ittraining.utente.Utente;

@Entity
public class TutorAziendale extends Utente {
	
	@OneToMany
	private List<Registro> registri;
	
	@OneToMany
	private List<ProgettoFormativo> progetti;
	
	@OneToOne
	private Azienda azienda;

	public TutorAziendale() {
		
	}

}
