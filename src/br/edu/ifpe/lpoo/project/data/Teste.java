package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.business.AcervoService;
import br.edu.ifpe.lpoo.project.data.implement.EbookRepository;
import br.edu.ifpe.lpoo.project.data.implement.LivroRepository;
import br.edu.ifpe.lpoo.project.data.implement.PeriodicoRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Ebook;
import br.edu.ifpe.lpoo.project.entities.acervo.Livro;
import br.edu.ifpe.lpoo.project.entities.acervo.Periodico;
import br.edu.ifpe.lpoo.project.enums.FormatoDigital;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
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
//		String titulo = "Contos Inacabados";
//		String autor = "J.R.R. Tolkien";
//		int anoPublicacao = 1980;
//		String editora = "HarperCollins";
//		String idioma = "Inglês";
//		String isbn = "9780261103627";
//		int numeroPaginas = 472;
//		String genero = "Fantasia";
//		FormatoDigital formatoDigital = FormatoDigital.PDF; 
//		String url = "https://www.exemplo.com/contos-inacabados.pdf";
		
//		Ebook ebook = new Ebook(titulo, autor, anoPublicacao, editora, idioma, isbn, numeroPaginas, genero, formatoDigital, url);
		
//		Ebook ebook1 = new Ebook("O Hobbit", "J.R.R. Tolkien", 1937, "HarperCollins", "Inglês", "9780547928227", 310, "Fantasia", FormatoDigital.PDF,
//				"https://www.exemplo.com/o-hobbit.pdf"
//			);
//		IEbookRepository ebookRepository = new EbookRepository();
		
//		acervo.cadastrarEbook("O Senhor dos Anéis", "J.R.R. Tolkien", "1954", "HarperCollins", "9780544003415", "1216", "Fantasia", "Inglês", "PDF", "https://www.exemplo.com/o-hobbit.pdf");
		
//		boolean teste = ebookRepository.exist(ebook1);
//		System.out.println(teste);
//		if(!teste) {
//			try {
//				ebookRepository.insert(ebook1);
//				System.out.println("Ebook cadastrado");
//			}catch(DbException e) {
//				System.out.println(e.getMessage());
//			}
//		}else {
//			System.out.println("Livro já existe!");
//		}

		
//		---------------------Periodico-------------------------------------------------------
		
//		String titulo = "Revista Ciência Hoje";
//		String autor = "Vários Autores";
//		String anoPublicacao = "2023";
//		String editora = "Sociedade Brasileira";
//		String idioma = "Português";
//		String isbn = "1234-5678";
//		String numeroEdicao = "42";
//		String volume = "7";
//		String genero = "Divulgação Científica";
		
		//Periodico periodico = new Periodico(titulo, autor, 2023, editora, idioma, isbn, 42, 7, genero); 
		
//		IPeriodicoRepository periodicoRepository = new PeriodicoRepository();
		//Chamado método insert
		//periodicoRepository.insert(periodico);
		
		//Chamando método exist
		//boolean existe = periodicoRepository.exist(periodico);
		//System.out.println(existe);
		
		//Testando regra
//		AcervoService acervo = new AcervoService();
//		
//		try {
//			acervo.cadastrarPeriodico(titulo, autor, anoPublicacao, isbn, editora, numeroEdicao, volume, genero, idioma);
//		}catch(BusinessExcepition e) {
//			System.out.println(e.getMessage());
//		}catch(DbException i) {
//			System.out.println(i.getMessage());
//		}
		
//		-------------------- Encontar por id----------------------------
//		ILivroRepository livroRepository = new LivroRepository();
//		
//		Livro livro = livroRepository.buscarPorId(4);
//		
//		if(livro == null) {
//			System.out.println("Livro não encontrado");
//		}else {
//			System.out.println(livro.getId());
//			System.out.println(livro.getIsbn());
//			System.out.println(livro.getNumeroPaginas());
//			System.out.println(livro.getGenero());
//			System.out.println(livro.getTitulo());
//			System.out.println(livro.getAutor());
//			System.out.println(livro.getAnoPublicacao());
//			System.out.println(livro.getEditora());
//			System.out.println(livro.getIdioma());
//			
//		}
		
//		IEbookRepository ebookRepository = new EbookRepository();
//		
//		Ebook ebook = ebookRepository.buscarPorId(3);
//		
//		if(ebook == null) {
//			System.out.println("Ebook não encontrado");
//		}else {
//			System.out.println(ebook.getId());
//			System.out.println(ebook.getIsbn());
//			System.out.println(ebook.getNumeroPaginas());
//			System.out.println(ebook.getGenero());
//			System.out.println(ebook.getTitulo());
//			System.out.println(ebook.getAutor());
//			System.out.println(ebook.getAnoPublicacao());
//			System.out.println(ebook.getEditora());
//			System.out.println(ebook.getIdioma());
//			System.out.println(ebook.getFormatoDigital().name());
//			System.out.println(ebook.getUrl());
//		}	
		
//		IPeriodicoRepository periodicoRepository = new PeriodicoRepository();
//		
//		Periodico periodico = periodicoRepository.buscarPorId(1);
//		if(periodico == null) {
//			System.out.println("Periodico não encontrado");
//		}else {
//			System.out.println(periodico);
//		}
	}
}
