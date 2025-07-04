package br.edu.ifpe.lpoo.project.enums;

public enum TipoItemAcervo {
    LIVRO("Livro"),
    EBOOK("E-book"),
    PERIODICO("Periódico");

    private final String descricao;

    TipoItemAcervo(String descricao) {this.descricao = descricao;}

    public String getDescricao() {return descricao;}

    public static TipoItemAcervo fromDescricao(String descricao) {
        for (TipoItemAcervo tipo : TipoItemAcervo.values()) {
            if (tipo.descricao.equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Nenhum TipoItemAcervo encontrado para a descrição: " + descricao);
    }
}
