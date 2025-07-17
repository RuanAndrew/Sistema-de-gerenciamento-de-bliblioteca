package br.edu.ifpe.lpoo.project.entities.acervo;

import br.edu.ifpe.lpoo.project.enums.StatusExemplar;
import br.edu.ifpe.lpoo.project.enums.TipoExemplar;
import br.edu.ifpe.lpoo.project.enums.TipoItemAcervo;

public class Exemplar {
	
	private int idExemplar;
	private int idItem;
	private TipoItemAcervo tipoItemAcervo;
	private String registro;
	private StatusExemplar status;
	private TipoExemplar tipoExemplar;

	public Exemplar(int idItem, TipoItemAcervo tipoItemAcervo, String registro, StatusExemplar status, TipoExemplar tipoExemplar) {
		this.idItem = idItem;
		this.tipoItemAcervo = tipoItemAcervo;
		this.registro = registro;
		this.status = status;
		this.tipoExemplar = tipoExemplar;
	}

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public int getIdExemplar() {
		return idExemplar;
	}

	public void setIdExemplar(int idExemplar) {
		this.idExemplar = idExemplar;
	}

	public TipoItemAcervo getTipoItemAcervo() {
		return tipoItemAcervo;
	}

	public void setTipoItemAcervo(TipoItemAcervo tipoItemAcervo) {
		this.tipoItemAcervo = tipoItemAcervo;
	}

	public StatusExemplar getStatus() {
		return status;
	}

	public void setStatus(StatusExemplar status) {
		this.status = status;
	}

	public TipoExemplar getTipoExemplar() {
		return tipoExemplar;
	}

	public void setTipoExemplar(TipoExemplar tipoExemplar) {
		this.tipoExemplar = tipoExemplar;
	}
	
	
}
