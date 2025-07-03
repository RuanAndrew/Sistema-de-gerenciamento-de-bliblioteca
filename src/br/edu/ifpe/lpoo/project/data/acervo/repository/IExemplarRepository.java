package br.edu.ifpe.lpoo.project.data.acervo.repository;

import java.util.List;

import java.util.List;

import br.edu.ifpe.lpoo.project.entities.acervo.Exemplar;

public interface IExemplarRepository {

<<<<<<< HEAD:src/br/edu/ifpe/lpoo/project/data/IExemplarRepository.java
	void insert(Exemplar exemplar, int idLivro);

	boolean existItem(Exemplar exemplar);

	void atualizarStatus(Exemplar exemplar);

	Exemplar buscarPorId(int idExemplar);

	void deleteParaLivros(int idLivro);

	List<Exemplar> buscarTodosPorIdLivro(int idItem);

=======
	boolean existItem(Exemplar exemplar);

	void insert(Exemplar exemplar, int idLivro);

	void deleteParaLivros(int idLivro);

	void atualizarStatus(Exemplar exemplar);

	public Exemplar buscarPorId(int idItem);

	public List<Exemplar> buscarTodosPorIdLivro(int idItem);
>>>>>>> c10cafe08840543bbd6f94d051672d23daf1e460:src/br/edu/ifpe/lpoo/project/data/acervo/repository/IExemplarRepository.java
}
