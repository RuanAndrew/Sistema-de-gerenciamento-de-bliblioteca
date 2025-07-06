package br.edu.ifpe.lpoo.project.entities.membros;

import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;

public class Aluno extends Membro {
	private String curso;

	public Aluno(String nome, String email, String cpf, String matricula, TipoMembro tipomembro, int debitomultas, StatusMembro statusmembro, String curso) {
		super(nome, email, cpf, matricula, tipomembro, debitomultas, statusmembro);
		this.curso = curso;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}
}


