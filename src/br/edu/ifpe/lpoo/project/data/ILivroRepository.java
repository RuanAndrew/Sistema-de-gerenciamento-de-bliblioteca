package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.entities.acervo.ItemAcervo;

public interface ILivroRepository {
	void insert(ItemAcervo item);

	boolean existItem(String indentifier);
	
}
