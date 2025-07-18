package br.edu.ifpe.lpoo.project.entities.acervo;

public abstract class ItemAcervo {
	
	private String titulo;
	private String autor;
	private int anoPublicacao;
	private String editora;
	private String idioma;
	private String localCapaPath;
	private int id;

	public ItemAcervo(String titulo, String autor, int anoPublicacao, String editora, String idioma, String localCapaPath) {
		this.titulo = titulo;
		this.autor = autor;
		this.anoPublicacao = anoPublicacao;
		this.editora = editora;
		this.idioma = idioma;
		this.localCapaPath = localCapaPath;
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

	public int getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(int anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocalCapaPath() {
		return localCapaPath;
	}

	public void setLocalCapaPath(String localCapaPath) {
		this.localCapaPath = localCapaPath;
	}
}
