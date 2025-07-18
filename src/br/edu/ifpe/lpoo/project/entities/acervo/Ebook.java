package br.edu.ifpe.lpoo.project.entities.acervo;

import br.edu.ifpe.lpoo.project.enums.FormatoDigital;

public class Ebook extends ItemAcervo{
    private String isbn;
    private int numeroPaginas;
    private String genero;
    private FormatoDigital formatoDigital;
    private String url;

    public Ebook(String titulo, String autor, int anoPublicacao, String editora, String idioma, String isbn, int numeroPaginas, String genero, FormatoDigital formatoDigital, String url) {
        super(titulo, autor, anoPublicacao, editora, idioma, null);
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.genero = genero;
        this.formatoDigital = formatoDigital;
        this.url = url;
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

    public FormatoDigital getFormatoDigital() {
        return formatoDigital;
    }

    public void setFormatoDigital(FormatoDigital formatoDigital) {
        this.formatoDigital = formatoDigital;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
