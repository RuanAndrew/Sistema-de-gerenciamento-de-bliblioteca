package br.edu.ifpe.lpoo.project.enums;

public enum TipoExemplar {
	
	FISICO("Físico"),
    DIGITAL("Digital");

    private final String descricao;

    TipoExemplar(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoExemplar fromDescricao(String descricao) {
        for (TipoExemplar tipo : TipoExemplar.values()) {
            if (tipo.descricao.equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Nenhum Tipo de exemplar encontrado para a descrição: " + descricao);
    }
}
