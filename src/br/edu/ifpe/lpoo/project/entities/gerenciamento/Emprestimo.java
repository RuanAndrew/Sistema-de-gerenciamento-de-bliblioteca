package br.edu.ifpe.lpoo.project.entities.gerenciamento;

import java.time.LocalDate;

import br.edu.ifpe.lpoo.project.enums.TipoItemAcervo;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;

public class Emprestimo {

	private int idEmprestimo;
	private int idItemAcervo;
	private int idMembro;
	private TipoItemAcervo tipoItemAcervo;
	private TipoMembro tipoMembro;
	private LocalDate dataEmprestimo;
	private LocalDate dataParaDevolucao;
	private LocalDate dataDevolucao;

	public Emprestimo(int idItemAcervo, int idMembro, TipoItemAcervo tipoItemAcervo, TipoMembro tipoMembro,
			LocalDate dataEmprestimo, LocalDate dataParaDevolucao, LocalDate dataDevolucao) {
		super();
		this.idItemAcervo = idItemAcervo;
		this.idMembro = idMembro;
		this.tipoItemAcervo = tipoItemAcervo;
		this.tipoMembro = tipoMembro;
		this.dataEmprestimo = dataEmprestimo;
		this.dataParaDevolucao = dataParaDevolucao;
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

	public int getIdMembro() {
		return idMembro;
	}

	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
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

}
