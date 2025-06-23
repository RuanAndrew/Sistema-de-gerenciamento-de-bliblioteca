package br.edu.ifpe.lpoo.project.data;

import java.util.List;

import br.edu.ifpe.lpoo.project.data.implement.LivroRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Livro;

public class Teste {

	public static void main(String[] args) {

		ILivroRepository livroRepository = new LivroRepository();
		
		List<Livro> livros = livroRepository.buscarTodos();
		
		
	}
}
