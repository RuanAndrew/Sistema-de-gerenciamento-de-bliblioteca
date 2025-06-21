package br.edu.ifpe.lpoo.project.entities.acervo;

public class Livro extends ItemArcevo {
	
	private String isbn;
	private int numeroPaginas;
	private String genero;

	public Livro(String titulo, String autor, int anoPublicacao, String editora, String idioma, String isbn, int numeroPaginas, String genero) {
		super(titulo, autor, anoPublicacao, editora, idioma);
		this.isbn = isbn;
		this.numeroPaginas = numeroPaginas;
		this.genero = genero;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getNumeroPaginas() {
		return numeroPaginas;
	}

	public void setNumeroPaginas(int numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}
}

