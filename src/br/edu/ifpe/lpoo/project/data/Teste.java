package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.business.AcervoService;

public class Teste {

	public static void main(String[] args) {
		
//		String titulo = "O Senhor dos Anéis";
//		String autor = "J.R.R. Tolkien";
//		int anoPublicacao = 1954;
//		String editora = "HarperCollins";
//		String isbn = "9780544003415"; // ISBN real da edição em inglês
//		int numeroPaginas = 1216; // Considerando a trilogia em volume único
//		String genero = "Fantasia";
//		String idioma = "Inglês";
		
		String titulo = "O Hobbit";
		String autor = "J.R.R. Tolkien";
		String anoPublicacao = "1937";
		String editora = "HarperCollins";
		String isbn = "9780547928227"; // ISBN de uma edição comum em inglês
		String numeroPaginas = "310";
		String genero = "Fantasia";
		String idioma = "Inglês";
		
		AcervoService acervo = new AcervoService();
		acervo.CadastrarLivro(titulo, autor, anoPublicacao, editora, isbn, numeroPaginas, genero, idioma, "3");
		
		
		
		
	}

}
