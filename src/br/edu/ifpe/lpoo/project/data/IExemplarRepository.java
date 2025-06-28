package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.entities.acervo.Exemplar;

public interface IExemplarRepository {

	boolean existItem(String indentifier);
	
	void insert(Exemplar exemplar, int idLivro);
	
	void deleteParaLivros(int idLivro);
	
	void atualizarStatus(Exemplar exemplar);
}
