package br.edu.ifpe.lpoo.project.entities.membros;

import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;

public class Pesquisador extends Membro {
	private String instituicao;


	public Pesquisador(String nome, String email, String cpf, String matricula, TipoMembro tipomembro, int debitomultas, StatusMembro statusmembro, String instituicao) {
		super(nome, email, cpf, matricula, tipomembro, debitomultas, statusmembro);
		this.instituicao = instituicao;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
}
