package br.edu.ifpe.lpoo.project.entities.acervo;

public class Periodico extends ItemAcervo{

    private String isbn;
    private int numeroEdicao;
    private int volume;
    private String dataPublicacao;
    private String genero;

    public Periodico(String titulo, String autor, int anoPublicacao, String editora, String idioma, String isbn, int numeroEdicao, int volume, String genero) {
        super(titulo, autor, anoPublicacao, editora, idioma);
        this.isbn = isbn;
        this.numeroEdicao = numeroEdicao;
        this.volume = volume;
        this.genero = genero;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumeroEdicao() {
        return numeroEdicao;
    }

    public void setNumeroEdicao(int numeroEdicao) {
        this.numeroEdicao = numeroEdicao;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

}
