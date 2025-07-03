package br.edu.ifpe.lpoo.project.data.acervo.repository;

import java.util.List;

import br.edu.ifpe.lpoo.project.entities.acervo.Ebook;

public interface IEbookRepository {
	
	void insert(Ebook ebook);
	
	boolean existItem(Ebook ebook);
	
	Ebook buscarPorId(int idItem);
	
	void delete(int idItem);
	
	List<Ebook> buscarTodos();
	
	List<Ebook> buscarPorTermo(String termo);
	
	void atualizar(Ebook ebook);
}
