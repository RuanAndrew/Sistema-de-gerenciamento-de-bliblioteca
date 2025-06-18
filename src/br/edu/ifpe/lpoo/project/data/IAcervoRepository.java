package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.entities.Exemplar;
import br.edu.ifpe.lpoo.project.entities.ItemArcevo;

public interface IAcervoRepository {

	int insert(ItemArcevo item);

	boolean existItem(String indentifier);
	
	void insertExemplar(Exemplar exemplar, int idLivro);
}
