package br.edu.ifpe.lpoo.project.entities.membros;

import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;

public class Professor extends Membro {
	private String areaAtuacao;
	private String departamento;

	public Professor(String nome, String email, String cpf, String matricula, TipoMembro tipomembro, int debitomultas, StatusMembro statusmembro, String areaAtuacao, String departamento) {
		super(nome, email, cpf, matricula, tipomembro, debitomultas, statusmembro);
		this.areaAtuacao = areaAtuacao;
		this.departamento = departamento;
	}

	public String getAreaAtuacao() {
		return areaAtuacao;
	}

	public void setAreaAtuacao(String areaAtuacao) {
		this.areaAtuacao = areaAtuacao;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
}
