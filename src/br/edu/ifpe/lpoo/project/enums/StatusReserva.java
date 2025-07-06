package br.edu.ifpe.lpoo.project.enums;

public enum StatusReserva {
	
	ATIVA("Ativa"),
	EXPIRADA("Expirada");
	
	private final String descricao;

    StatusReserva(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusReserva fromDescricao(String descricao) {
        for (StatusReserva status : StatusReserva.values()) {
            if (status.descricao.equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Nenhum StatusReserva encontrado para a descrição: " + descricao);
    }
}
