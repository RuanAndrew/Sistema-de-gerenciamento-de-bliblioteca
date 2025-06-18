package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.entities.ItemArcevo;

public interface IAcervoRepository {

	void insert(ItemArcevo item);

	boolean existItem(String indentifier);
}
