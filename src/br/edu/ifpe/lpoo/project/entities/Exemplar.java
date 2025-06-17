package br.edu.ifpe.lpoo.project.entities;

public class Exemplar {
	
	private int idLivro;
	private String registro;
	private boolean disponivel;
	
	public Exemplar(int idLivro, String registro, boolean disponivel) {
		super();
		this.idLivro = idLivro;
		this.registro = registro;
		this.disponivel = disponivel;
	}

	public int getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(int idLivro) {
		this.idLivro = idLivro;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}
	
}
