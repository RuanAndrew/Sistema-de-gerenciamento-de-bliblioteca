package br.edu.ifpe.lpoo.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Importe esta anotação

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorPojo {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
