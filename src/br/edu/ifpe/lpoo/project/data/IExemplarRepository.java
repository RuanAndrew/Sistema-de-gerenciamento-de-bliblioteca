package br.edu.ifpe.lpoo.project.data;

import java.util.List;

import br.edu.ifpe.lpoo.project.entities.acervo.Exemplar;

public interface IExemplarRepository {

	void insert(Exemplar exemplar, int idLivro);

	boolean existItem(Exemplar exemplar);

	void atualizarStatus(Exemplar exemplar);

	Exemplar buscarPorId(int idExemplar);

	void deleteParaLivros(int idLivro);

	List<Exemplar> buscarTodosPorIdLivro(int idItem);

}
