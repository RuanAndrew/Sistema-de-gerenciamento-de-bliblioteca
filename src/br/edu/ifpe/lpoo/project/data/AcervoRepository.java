package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.entities.ItemArcevo;

public interface AcervoRepository {
	
	public void insert(ItemArcevo item);
	
	public boolean existIten(String indentifier);
}
