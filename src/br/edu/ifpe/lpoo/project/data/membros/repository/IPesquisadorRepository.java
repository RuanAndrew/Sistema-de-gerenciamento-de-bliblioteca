package br.edu.ifpe.lpoo.project.data.membros.repository;

import java.util.List;

import br.edu.ifpe.lpoo.project.entities.membros.Pesquisador;

public interface IPesquisadorRepository {
	
	void insert(Pesquisador pesquisador);
	
	boolean existMembro(Pesquisador pesquisador);
	
	void delete(int idMembro);
	
	Pesquisador buscarPorId(int idMembro);
	
	List<Pesquisador> buscarTodos();
	
	List<Pesquisador> buscarPorTermo(String termo);
	
	void atualizar(Pesquisador pesquisador);
}
