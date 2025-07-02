package br.edu.ifpe.lpoo.project.entities.gerenciamento;

import java.time.LocalDate;

import br.edu.ifpe.lpoo.project.enums.TipoItemAcervo;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;

public class Emprestimo {

	private int idEmprestimo;
	private int idItemAcervo;
	private int idAluno;
	private TipoItemAcervo tipoItemAcervo;
	private TipoMembro tipoMembro;
	private String tipoItem;
	private LocalDate dataEmprestimo;
	private LocalDate dataDevolucao;

	public Emprestimo(int idItemAcervo, int idAluno, TipoItemAcervo tipoItemAcervo, TipoMembro tipoMembro,
			String tipoItem, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
		super();
		this.idItemAcervo = idItemAcervo;
		this.idAluno = idAluno;
		this.tipoItemAcervo = tipoItemAcervo;
		this.tipoMembro = tipoMembro;
		this.tipoItem = tipoItem;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucao = dataDevolucao;
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

	public int getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}

	public TipoItemAcervo getTipoItemAcervo() {
		return tipoItemAcervo;
	}

	public void setTipoItemAcervo(TipoItemAcervo tipoItemAcervo) {
		this.tipoItemAcervo = tipoItemAcervo;
	}

	public TipoMembro getTipoMembro() {
		return tipoMembro;
	}

	public void setTipoMembro(TipoMembro tipoMembro) {
		this.tipoMembro = tipoMembro;
	}

	public String getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(String tipoItem) {
		this.tipoItem = tipoItem;
	}

	public LocalDate getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(LocalDate dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
}
