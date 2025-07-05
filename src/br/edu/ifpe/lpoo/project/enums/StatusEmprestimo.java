package br.edu.ifpe.lpoo.project.enums;

public enum StatusEmprestimo {
	
	ABERTO("Aberto"),
    ATRASADO("Atradaso"),
    FINALIZADO("Finalizado");

    private final String descricao;

    StatusEmprestimo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusEmprestimo fromDescricao(String descricao) {
        for (StatusEmprestimo status : StatusEmprestimo.values()) {
            if (status.descricao.equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Nenhum StatusEmprestimo encontrado para a descrição: " + descricao);
    }
}
