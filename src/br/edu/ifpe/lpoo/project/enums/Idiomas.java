package br.edu.ifpe.lpoo.project.enums;

public enum Idiomas {
    PORTUGUES("Português"),
    INGLES("Inglês"),
    ESPANHOL("Espanhol"),
    FRANCES("Francês"),
    ALEMAO("Alemão");

    private final String descricao;

    Idiomas(String descricao) { this.descricao = descricao; }

    public String getDescricao() { return descricao; }

    public static Idiomas fromDescricao(String descricao) {
        for (Idiomas i : Idiomas.values()) {
            if (i.descricao.equalsIgnoreCase(descricao)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Idiomas inválido: " + descricao);
    }
}
