package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.entities.membros.Pesquisador;

public interface IPesquisadorRepository {
	
	void insert(Pesquisador pesquisador);
	
	boolean existMembro(Pesquisador pesquisador);
	
	void delete(int idMembro);
}
