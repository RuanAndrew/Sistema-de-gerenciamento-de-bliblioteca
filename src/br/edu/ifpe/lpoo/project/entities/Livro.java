package br.edu.ifpe.lpoo.project.entities;

public class Livro {
	private String isbn;
	private String titulo;
	private String autor;
	private String editora;
	private String genero;
	private int numeroPaginas;
	private String anoPublicação;
	
	public Livro(String isbn, String titulo, String autor, String editora, int numeroPaginas, String anoPublicação, String genero) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.editora = editora;
		this.numeroPaginas = numeroPaginas;
		this.anoPublicação = anoPublicação;
		this.genero = genero;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
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

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getNumeroPaginas() {
		return numeroPaginas;
	}

	public void setNumeroPaginas(int numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}

	public String getAnoPublicação() {
		return anoPublicação;
	}

	public void setAnoPublicação(String anoPublicação) {
		this.anoPublicação = anoPublicação;
	}
	
	
	
}
