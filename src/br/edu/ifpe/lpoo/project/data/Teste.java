package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.business.AcervoService;
import br.edu.ifpe.lpoo.project.data.implement.EbookRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Ebook;
import br.edu.ifpe.lpoo.project.enums.FormatoDigital;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class Teste {

	public static void main(String[] args) {

//		----------------------------Teste livro--------------------
//		String titulo = "O Senhor dos Anéis";
//		String autor = "J.R.R. Tolkien";
//		int anoPublicacao = 1954;
//		String editora = "HarperCollins";
//		String isbn = "9780544003415"; // ISBN real da edição em inglês
//		int numeroPaginas = 1216; // Considerando a trilogia em volume único
//		String genero = "Fantasia";
//		String idioma = "Inglês";
		
//		String titulo = "O Hobbit";
//		String autor = "J.R.R. Tolkien";
//		String anoPublicacao = "1937";
//		String editora = "HarperCollins";
//		String isbn = "9780547928227"; // ISBN de uma edição comum em inglês
//		String numeroPaginas = "310";
//		String genero = "Fantasia";
//		String idioma = "Inglês";
//		
//		AcervoService acervo = new AcervoService();
//		acervo.cadastrarLivro(titulo, autor, anoPublicacao, editora, isbn, numeroPaginas, genero, idioma, "3");
		
//		---------------------------Teste ebook----------------------------------------------
		String titulo = "Contos Inacabados";
		String autor = "J.R.R. Tolkien";
		int anoPublicacao = 1980;
		String editora = "HarperCollins";
		String idioma = "Inglês";
		String isbn = "9780261103627";
		int numeroPaginas = 472;
		String genero = "Fantasia";
		FormatoDigital formatoDigital = FormatoDigital.PDF; 
		String url = "https://www.exemplo.com/contos-inacabados.pdf";
		
		Ebook ebook = new Ebook(titulo, autor, anoPublicacao, editora, idioma, isbn, numeroPaginas, genero, formatoDigital, url);
		
		Ebook ebook1 = new Ebook("O Hobbit", "J.R.R. Tolkien", 1937, "HarperCollins", "Inglês", "9780547928227", 310, "Fantasia", FormatoDigital.PDF,
				"https://www.exemplo.com/o-hobbit.pdf"
			);
		IEbookRepository ebookRepository = new EbookRepository();
		
		boolean teste = ebookRepository.exist(ebook1);
		System.out.println(teste);
		if(!teste) {
			try {
				ebookRepository.insert(ebook1);
				System.out.println("Ebook cadastrado");
			}catch(DbException e) {
				System.out.println(e.getMessage());
			}
		}else {
			System.out.println("Livro já existe!");
		}

		
	}

}
