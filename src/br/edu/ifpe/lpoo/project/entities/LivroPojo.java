package br.edu.ifpe.lpoo.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Importe esta anotação

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroPojo {
	
	private DetalhesLivroPojo details;
	
	public LivroPojo() {
		
	}
	
	public DetalhesLivroPojo getDetails() {
		return details;
	}

	public void setDetails(DetalhesLivroPojo details) {
		this.details = details;
	}
	
}
