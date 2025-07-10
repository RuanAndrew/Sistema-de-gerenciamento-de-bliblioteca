package br.edu.ifpe.lpoo.project.data.acervo.repository;

import java.util.List;

import br.edu.ifpe.lpoo.project.entities.acervo.Livro;

public interface ILivroRepository {
	void insert(Livro livro);

	boolean existItem(int idItem);
	
	Livro buscarPorId(int idItem);
	
	void delete(int idItem);
	
	List<Livro> buscarTodos();
	
	List<Livro> buscarPorTermo(String termo);
	
	void atualizar(Livro livro);
}
