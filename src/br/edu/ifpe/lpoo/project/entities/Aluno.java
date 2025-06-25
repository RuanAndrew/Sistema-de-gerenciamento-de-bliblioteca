package br.edu.ifpe.lpoo.project.entities;

import java.time.LocalDate;

public class Aluno extends Membro {
	
	private String telefone;
	private LocalDate data;
	private String curso;
	
	public Aluno(String nome, String email, String cpf, String matricula, String tipomembro, String debitomultas, String statusmembro,String telefone, LocalDate data, String curso) {
		super(nome, email, cpf, matricula, tipomembro, debitomultas, statusmembro);
		this.telefone = telefone;
		this.data = data;
		this.curso = curso;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
	public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
	
	
	
}


