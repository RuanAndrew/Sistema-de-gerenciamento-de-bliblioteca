package br.edu.ifpe.lpoo.project.data.acervo.repository;

import java.util.List;

import br.edu.ifpe.lpoo.project.entities.acervo.Exemplar;

public interface IExemplarRepository {

	boolean existItem(Exemplar exemplar);

	void insert(Exemplar exemplar, int idLivro);

	void deleteParaLivros(int idLivro);

	void atualizarStatus(Exemplar exemplar);

	public Exemplar buscarPorId(int idItem);

	public List<Exemplar> buscarTodosPorIdLivro(int idItem);
}
