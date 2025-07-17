package br.edu.ifpe.lpoo.project.data.acervo.repository;

import java.sql.Connection;
import java.util.List;

import br.edu.ifpe.lpoo.project.entities.acervo.Exemplar;
import br.edu.ifpe.lpoo.project.enums.StatusExemplar;
import br.edu.ifpe.lpoo.project.enums.TipoItemAcervo;

public interface IExemplarRepository {

	boolean existItem(Exemplar exemplar);

	void insert(Exemplar exemplar, int idLivro);
	
	boolean existPorId(int idExemplar);
	
	void deleteParaLivros(int idLivro, Connection conn);

	void atualizarStatus(int idExemplar, StatusExemplar statusExemplar);

	Exemplar buscarPorId(int idItem);

	List<Exemplar> buscarTodosPorIdLivro(int idItem);
	
	int getMaiorRegistro(int idExemplar, TipoItemAcervo tipoItemAcervo);
}
