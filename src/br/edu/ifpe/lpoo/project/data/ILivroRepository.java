package br.edu.ifpe.lpoo.project.data;

import java.util.List;

import br.edu.ifpe.lpoo.project.entities.acervo.ItemAcervo;
import br.edu.ifpe.lpoo.project.entities.acervo.Livro;

public interface ILivroRepository {
	void insert(ItemAcervo item);

	boolean existItem(String indentifier);
	
	Livro buscarPorId(int idItem);
	
	List<Livro> buscarTodos();
	
	List<Livro> buscarPorTermo(String termo);
}
