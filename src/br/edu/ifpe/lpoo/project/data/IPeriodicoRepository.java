package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.entities.acervo.ItemAcervo;
import br.edu.ifpe.lpoo.project.entities.acervo.Periodico;

public interface IPeriodicoRepository {
	
	void insert(ItemAcervo item);
	
	boolean exist(ItemAcervo item);	
	
	Periodico buscarPorId(int idItem);
}
