package br.edu.ifpe.lpoo.project.ui.dto;

import br.edu.ifpe.lpoo.project.enums.TipoItemAcervo;

public class EbookDTO {
    private int id;
    private TipoItemAcervo tipoItemAcervo = TipoItemAcervo.EBOOK;
    private String titulo;
    private String autor;
    private String anoPublicacao;
    private String editora;
    private String isbn;
    private String numeroPaginas;
    private String genero;
    private String idioma;
    private String formatoDigital;
    private String url;

    public EbookDTO(String titulo, String autor, String anoPublicacao, String editora, String isbn, String numeroPaginas, String genero, String idioma, String formatoDigital, String url) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.genero = genero;
        this.idioma = idioma;
        this.formatoDigital = formatoDigital;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(String numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getFormatoDigital() {
        return formatoDigital;
    }

    public void setFormatoDigital(String formatoDigital) {
        this.formatoDigital = formatoDigital;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TipoItemAcervo getTipoItemAcervo() {
        return tipoItemAcervo;
    }

    public void setTipoItemAcervo(TipoItemAcervo tipoItemAcervo) {
        this.tipoItemAcervo = tipoItemAcervo;
    }
}
