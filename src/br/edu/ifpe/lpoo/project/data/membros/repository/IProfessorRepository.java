package br.edu.ifpe.lpoo.project.data.membros.repository;

import java.util.List;

import br.edu.ifpe.lpoo.project.entities.membros.Professor;

public interface IProfessorRepository {
	
	void insert (Professor professor);
	
	boolean existMembro(Professor professor);
	
	void delete(int idMembro);
	
	Professor buscarPorId(int idMembro);
	
	List<Professor> buscarTodos();
	
	List<Professor> buscarPorTermo(String termo);
	
	void atualizar(Professor professor);
}
