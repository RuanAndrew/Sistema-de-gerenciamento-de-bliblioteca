package br.edu.ifpe.lpoo.project.enums;

public enum FormatoDigital {
    PDF("PDF"),
    EPUB("EPUB"),
    MOBI("MOBI");

    private final String descricao;

    FormatoDigital(String descricao) { this.descricao = descricao; }

    public String getDescricao() { return descricao; }

    public static FormatoDigital fromDescricao(String descricao) {
        for (FormatoDigital fd : FormatoDigital.values()) {
            if (fd.descricao.equalsIgnoreCase(descricao)) {
                return fd;
            }
        }
        throw new IllegalArgumentException("Formato Digital inválido: " + descricao);
    }
}
