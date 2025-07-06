package br.edu.ifpe.lpoo.project.entities.emprestimo;

import java.time.LocalDate;

import br.edu.ifpe.lpoo.project.enums.StatusReserva;

public class Reserva {

	private int idReserva;
	private int idMembro;
	private int idItemAcervo;
	private LocalDate dataReserva;
	private LocalDate dataExpiracaoReserva;
	private StatusReserva statusReserva;

	public Reserva(int idMembro, int idItemAcervo, LocalDate dataReserva, LocalDate dataExpiracaoReserva,
			StatusReserva statusReserva) {
		super();
		this.idMembro = idMembro;
		this.idItemAcervo = idItemAcervo;
		this.dataReserva = dataReserva;
		this.dataExpiracaoReserva = dataExpiracaoReserva;
		this.statusReserva = statusReserva;
	}

	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public int getIdMembro() {
		return idMembro;
	}

	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}

	public int getIdItemAcervo() {
		return idItemAcervo;
	}

	public void setIdItemAcervo(int idItemAcervo) {
		this.idItemAcervo = idItemAcervo;
	}

	public LocalDate getDataReserva() {
		return dataReserva;
	}

	public void setDataReserva(LocalDate dataReserva) {
		this.dataReserva = dataReserva;
	}

	public LocalDate getDataExpiracaoReserva() {
		return dataExpiracaoReserva;
	}

	public void setDataExpiraçãoReserva(LocalDate dataExpiracaoReserva) {
		this.dataExpiracaoReserva = dataExpiracaoReserva;
	}

	public StatusReserva getStatusReserva() {
		return statusReserva;
	}

	public void setStatusReserva(StatusReserva statusReserva) {
		this.statusReserva = statusReserva;
	}
}
