package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.entities.acervo.Ebook;
import br.edu.ifpe.lpoo.project.entities.acervo.ItemAcervo;

public interface IEbookRepository {
	
	void insert(ItemAcervo item);
	
	boolean exist(ItemAcervo item);
	
	Ebook buscarPorId(int idItem);
}
