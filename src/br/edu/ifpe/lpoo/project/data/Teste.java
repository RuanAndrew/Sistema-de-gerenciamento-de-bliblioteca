package br.edu.ifpe.lpoo.project.data;


import br.edu.ifpe.lpoo.project.data.implement.LivroRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Livro;
public class Teste {

	public static void main(String[] args) {

//		IEbookRepository ebookRepository = new EbookRepository();
//		
//		Ebook ebook = ebookRepository.buscarPorId(2);
//		
//		if(ebook == null) {
//			System.out.println("Ebook não existe ou já foi deletado");
//		}else {
//			ebookRepository.delete(ebook);
//			System.out.println("Ebook " + ebook.getTitulo() + " ");
//		}
//		
//		System.out.println("");
//		System.out.println("-------------------------------------");
//		System.out.println("");
//		
//		boolean exists = ebookRepository.exist(ebook);
//		System.out.println(exists);
		
//		IPeriodicoRepository periodicoRepository = new PeriodicoRepository();
//		
//		Periodico periodico = periodicoRepository.buscarPorId(1);
//		
//		if(periodico == null) {
//			System.out.println("Periódico não existe ou já foi deletado");
//		}else {
//			periodicoRepository.delete(periodico);
//			System.out.println("Livro " + periodico.getTitulo() + " deletado com sucesso");
//		}
//		
//		System.out.println("");
//		System.out.println("-------------------------------------");
//		System.out.println("");
//		
//		boolean exists = periodicoRepository.exist(periodico);
//		System.out.println(exists);
		
		ILivroRepository livroRepository = new LivroRepository();
		
		Livro livro = livroRepository.buscarPorId(3);
		
		if(livro == null) {
			System.out.println("Periódico não existe ou já foi deletado");
		}else {
			livroRepository.delete(livro.getId());
			System.out.println("Livro " + livro.getTitulo() + " deletado com sucesso");
		}
		
		System.out.println("");
		System.out.println("-------------------------------------");
		System.out.println("");
		
		boolean exists = livroRepository.existItem(livro.getIsbn());
		System.out.println(exists);
	}
}
