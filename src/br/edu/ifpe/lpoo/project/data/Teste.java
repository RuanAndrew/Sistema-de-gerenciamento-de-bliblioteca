package br.edu.ifpe.lpoo.project.data;

import java.util.List;

import br.edu.ifpe.lpoo.project.data.implement.EbookRepository;
import br.edu.ifpe.lpoo.project.data.implement.LivroRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Ebook;
import br.edu.ifpe.lpoo.project.entities.acervo.Livro;

public class Teste {

	public static void main(String[] args) {

		ILivroRepository livroRepository = new LivroRepository();
		
		List<Livro> livros = livroRepository.buscarTodos();
		
		for(Livro livro : livros) {
			System.out.println(livro.getId() + " " + livro.getTitulo());
		}
		
		System.out.println("-------------------------------------");
		
		livros = livroRepository.buscarPorTermo("J.R.");
		for(Livro livro : livros) {
			System.out.println(livro.getId() + " " + livro.getTitulo());
		}
		
		System.out.println("");
		System.out.println("-------------------------------------");
		System.out.println("");
		
		Livro livro = livroRepository.buscarPorId(1);
		System.out.println(livro.getId() + " " + livro.getTitulo());
		
		System.out.println("");
		System.out.println("-------------------------------------");
		System.out.println("");
		
		boolean exist = livroRepository.existItem(livro.getTitulo());
		System.out.println(exist);
		
		System.out.println("");
		System.out.println("-------------------------------------");
		System.out.println("-------------------------------------");
		System.out.println("");
		
		IEbookRepository ebookRepository = new EbookRepository();
		
		List<Ebook> ebooks = ebookRepository.buscarTodos();
		
		for(Ebook ebook : ebooks) {
			System.out.println(ebook.getId() + " " + ebook.getTitulo());
		}
		
		System.out.println("-------------------------------------");
		
		ebooks = ebookRepository.buscarPorTermo("J.R.");
		for(Ebook ebook : ebooks) {
			System.out.println(ebook.getId() + " " + ebook.getTitulo());
		}
		
		System.out.println("");
		System.out.println("-------------------------------------");
		System.out.println("");
		
		Ebook ebook = ebookRepository.buscarPorId(2);
		System.out.println(ebook.getId() + " " + ebook.getTitulo());
		
		System.out.println("");
		System.out.println("-------------------------------------");
		System.out.println("");
		
		boolean exists = ebookRepository.exist(ebook);
		System.out.println(exists);
	}
}
