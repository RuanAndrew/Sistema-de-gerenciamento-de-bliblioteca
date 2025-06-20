package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.entities.Exemplar;

public interface IExemplarRepository {

	boolean existItem(String indentifier);
	
	void insert(Exemplar exemplar, int idLivro);
}
