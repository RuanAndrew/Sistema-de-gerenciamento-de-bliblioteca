package br.edu.ifpe.lpoo.project.entities;

public abstract class ItemArcevo {
	
	private String titulo;
	private String autor;
	private String anoPublicacao;
	private String editora;
	
	public ItemArcevo(String titulo, String autor, String anoPublicacao, String editora) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.anoPublicacao = anoPublicacao;
		this.editora = editora;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(String anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}
	
	

}
