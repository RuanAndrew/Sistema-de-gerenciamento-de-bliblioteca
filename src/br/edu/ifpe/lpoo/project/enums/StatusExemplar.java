package br.edu.ifpe.lpoo.project.enums;

public enum StatusExemplar {
    DISPONIVEL("Disponível"),
    EMPRESTADO("Emprestado"),
    RESERVADO("Reservado"),
    DANIFICADO("Danificado"),
    EXTRAVIADO("Extraviado"),
    EM_MANUTENCAO("Em Manutenção");

    private final String descricao;

    StatusExemplar(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusExemplar fromDescricao(String descricao) {
        for (StatusExemplar status : StatusExemplar.values()) {
            if (status.descricao.equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Nenhum StatusExemplar encontrado para a descrição: " + descricao);
    }
}