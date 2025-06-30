package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.business.AcervoService;


public class Teste {

	public static void main(String[] args) {

		AcervoService acervoService = new AcervoService();

//		Cadastrar livro e exemplares com existItem ok
		acervoService.cadastrarLivro("O Senhor dos Anéis", "J.R.R. Tolkien", "1954", "HarperCollins", "9780261102385", "1216", "Fantasia", "Português", "3");
		acervoService.cadastrarLivro("Dom Casmurro", "Machado de Assis", "1899", "Editora Garnier", "9788535910667", "256", "Romance", "Português", "5");
		acervoService.cadastrarLivro("1984", "George Orwell", "1949", "Companhia das Letras", "9788535914849", "352", "Distopia", "Português", "4");
		acervoService.cadastrarLivro("A Revolução dos Bichos", "George Orwell", "1945", "Companhia das Letras", "9788535913958", "152", "Sátira", "Português", "2");
		acervoService.cadastrarLivro("Harry Potter e a Pedra Filosofal", "J.K. Rowling", "1997", "Rocco", "9788532530783", "223", "Fantasia", "Português", "6");
		
//		Cadastrar ebook com existItem ok
		acervoService.cadastrarEbook("O Hobbit", "J.R.R. Tolkien", "1937", "HarperCollins", "9788595084742", "320", "Fantasia", "Português", "PDF", "https://example.com/hobbit.pdf");
		acervoService.cadastrarEbook("Dom Casmurro", "Machado de Assis", "1899", "Editora Globo", "9788525042871", "256", "Romance", "Português", "EPUB", "https://example.com/domcasmurro.epub");
		acervoService.cadastrarEbook("1984", "George Orwell", "1949", "Companhia das Letras", "9788535909555", "416", "Distopia", "Português", "MOBI", "https://example.com/1984.mobi");
		acervoService.cadastrarEbook("A Revolução dos Bichos", "George Orwell", "1945", "Companhia das Letras", "9788535909556", "152", "Sátira", "Português", "PDF", "https://example.com/revolucao.pdf");
		acervoService.cadastrarEbook("Memórias Póstumas de Brás Cubas", "Machado de Assis", "1881", "Editora Ática", "9788535702873", "224", "Romance", "Português", "EPUB", "https://example.com/brascubas.epub");
		
		
//		Cadastrar periodico com existItem ok
		acervoService.cadastrarPeriodico("Revista Ciência Hoje", "Vários Autores", "2023", "0101-1234", "SBPC", "271", "38", "Ciência", "Português");
		acervoService.cadastrarPeriodico("Nature", "Editorial Nature", "2022", "0028-0836", "Springer Nature", "8001", "600", "Ciência", "Inglês");
		acervoService.cadastrarPeriodico("Revista História", "Vários Autores", "2021", "1980-4369", "Editora Unesp", "142", "29", "História", "Português");
		acervoService.cadastrarPeriodico("Scientific American Brasil", "Vários Autores", "2024", "1678-941X", "Duetto Editorial", "230", "45", "Divulgação Científica", "Português");


		
		
		
	}
}
