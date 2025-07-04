package br.edu.ifpe.lpoo.project.data.membros.repository;

import java.util.List;

import br.edu.ifpe.lpoo.project.entities.membros.Aluno;

public interface IAlunoRepository {
	
	void insert (Aluno aluno);
	
	boolean existMembro(Aluno aluno);
	
	void delete(int idMembro);
	
	Aluno buscarPorId(int idMembro);
	
	List<Aluno> buscarTodos();
	
	List<Aluno> buscarPorTermo(String termo);
	
	void atualizar(Aluno aluno);
}
