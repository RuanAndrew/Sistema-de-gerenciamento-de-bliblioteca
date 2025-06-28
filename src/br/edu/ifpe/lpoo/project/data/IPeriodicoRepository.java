package br.edu.ifpe.lpoo.project.data;


import java.util.List;

import br.edu.ifpe.lpoo.project.entities.acervo.ItemAcervo;
import br.edu.ifpe.lpoo.project.entities.acervo.Periodico;

public interface IPeriodicoRepository {
	
	void insert(ItemAcervo item);
	
	boolean exist(ItemAcervo item);	
	
	Periodico buscarPorId(int idItem);
	
	void delete(int idItem);
	
	List<Periodico> buscarTodos();
	
	List<Periodico> buscarPorTermo(String termo);
}
