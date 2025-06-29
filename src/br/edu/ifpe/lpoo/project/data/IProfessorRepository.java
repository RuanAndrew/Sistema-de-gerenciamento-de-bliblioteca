package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.entities.membros.Professor;

public interface IProfessorRepository {
	
	void insert (Professor professor);
	
	boolean existMembro(Professor professor);
	
	void delete(int idMembro);
	
	Professor buscarPorId(Integer idMembro);
}
