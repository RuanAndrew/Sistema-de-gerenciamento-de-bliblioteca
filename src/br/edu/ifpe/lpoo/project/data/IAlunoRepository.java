package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.entities.membros.Aluno;

public interface IAlunoRepository {
	
	void insert (Aluno aluno);
	
	boolean existMembro(Aluno aluno);
	
}
