package br.edu.ifpe.lpoo.project.entities.emprestimo;

import java.time.LocalDate;

import br.edu.ifpe.lpoo.project.enums.StatusEmprestimo;

public class Emprestimo {

	private int idEmprestimo;
	private int idItemAcervo;
	private int idMembro;
	private LocalDate dataEmprestimo;
	private LocalDate dataParaDevolucao;
	private LocalDate dataDevolucao;
	private StatusEmprestimo statusEmprestimo;

	public Emprestimo(int idItemAcervo, int idMembro, LocalDate dataEmprestimo, LocalDate dataParaDevolucao,
			LocalDate dataDevolucao, StatusEmprestimo statusEmprestimo) {
		super();
		this.idItemAcervo = idItemAcervo;
		this.idMembro = idMembro;
		this.dataEmprestimo = dataEmprestimo;
		this.dataParaDevolucao = dataParaDevolucao;
		this.dataDevolucao = dataDevolucao;
		this.statusEmprestimo = statusEmprestimo;
	}

	public int getIdEmprestimo() {
		return idEmprestimo;
	}

	public void setIdEmprestimo(int idEmprestimo) {
		this.idEmprestimo = idEmprestimo;
	}

	public int getIdItemAcervo() {
		return idItemAcervo;
	}

	public void setIdItemAcervo(int idItemAcervo) {
		this.idItemAcervo = idItemAcervo;
	}

	public int getIdMembro() {
		return idMembro;
	}

	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}

	public LocalDate getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(LocalDate dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public LocalDate getDataParaDevolucao() {
		return dataParaDevolucao;
	}

	public void setDataParaDevolucao(LocalDate dataParaDevolucao) {
		this.dataParaDevolucao = dataParaDevolucao;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public StatusEmprestimo getStatusEmprestimo() {
		return statusEmprestimo;
	}

	public void setStatusEmprestimo(StatusEmprestimo statusEmprestimo) {
		this.statusEmprestimo = statusEmprestimo;
	}
}
