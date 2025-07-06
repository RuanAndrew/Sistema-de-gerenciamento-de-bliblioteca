package br.edu.ifpe.lpoo.project.ui.dto;

import br.edu.ifpe.lpoo.project.enums.TipoItemAcervo;

public class PeriodicoDTO {
    private int id;
    private TipoItemAcervo tipoItemAcervo = TipoItemAcervo.PERIODICO;
    private String titulo;
    private String autor;
    private String anoPublicacao;
    private String editora;
    private String idioma;
    private String issn;
    private String numeroEdicao;
    private String volume;
    private String genero;

    public PeriodicoDTO(String titulo, String autor, String anoPublicacao, String editora, String idioma, String issn, String numeroEdicao, String volume, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.idioma = idioma;
        this.issn = issn;
        this.numeroEdicao = numeroEdicao;
        this.volume = volume;
        this.genero = genero;
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

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getNumeroEdicao() {
        return numeroEdicao;
    }

    public void setNumeroEdicao(String numeroEdicao) {
        this.numeroEdicao = numeroEdicao;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public TipoItemAcervo getTipoItemAcervo() {
        return tipoItemAcervo;
    }

    public void setTipoItemAcervo(TipoItemAcervo tipoItemAcervo) {
        this.tipoItemAcervo = tipoItemAcervo;
    }
}
