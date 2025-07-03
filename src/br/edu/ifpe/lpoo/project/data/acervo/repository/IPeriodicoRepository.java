package br.edu.ifpe.lpoo.project.data.acervo.repository;


import java.util.List;

import br.edu.ifpe.lpoo.project.entities.acervo.Periodico;

public interface IPeriodicoRepository {
	
	void insert(Periodico periodico);
	
	boolean existItem(Periodico periodico);	
	
	Periodico buscarPorId(int idItem);
	
	void delete(int idItem);
	
	List<Periodico> buscarTodos();
	
	List<Periodico> buscarPorTermo(String termo);
	
	void atualizar(Periodico periodico);
}
