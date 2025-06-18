package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.entities.Exemplar;
import br.edu.ifpe.lpoo.project.entities.Livro;
import br.edu.ifpe.lpoo.project.enums.StatusExemplar;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

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
		int anoPublicacao = 1937;
		String editora = "HarperCollins";
		String isbn = "9780547928227"; // ISBN de uma edição comum em inglês
		int numeroPaginas = 310;
		String genero = "Fantasia";
		String idioma = "Inglês";
		
		Livro livro = new Livro(titulo,autor, anoPublicacao, editora, idioma, isbn, numeroPaginas, genero);
		
		Exemplar exemplar;
		
		IAcervoRepository inserindoLivro = new AcervoRepository();
		IAcervoRepository inserindoExemplar = new AcervoRepository();
		
		try {
//			Teste insert
			int id_livro = inserindoLivro.insert(livro);
			
//			Teste exists
			boolean exists = inserindoLivro.existItem("O Senhor dos Anéio");
			
			for(int i = 0; i < 3; i++) {
				String registro = id_livro + "EX" + (i+1);
				exemplar = new Exemplar(id_livro, registro, StatusExemplar.DISPONIVEL);
				inserindoExemplar.insertExemplar(exemplar, id_livro);
			}
			
			System.out.println(exists);
		}catch(DbException e ) {
			System.out.println(e.getMessage());
		}
	}

}
