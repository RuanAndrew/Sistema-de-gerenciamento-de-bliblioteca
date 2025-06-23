package br.edu.ifpe.lpoo.project.data;

import java.util.List;

import br.edu.ifpe.lpoo.project.data.implement.EbookRepository;
import br.edu.ifpe.lpoo.project.data.implement.LivroRepository;
import br.edu.ifpe.lpoo.project.data.implement.PeriodicoRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Ebook;
import br.edu.ifpe.lpoo.project.entities.acervo.Livro;
import br.edu.ifpe.lpoo.project.entities.acervo.Periodico;

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
		
		IPeriodicoRepository periodicoRepository = new PeriodicoRepository();
		
		Periodico periodico = periodicoRepository.buscarPorId(1);
		
		if(periodico == null) {
			System.out.println("Periódico não existe ou já foi deletado");
		}else {
			periodicoRepository.delete(periodico);
			System.out.println("Livro " + periodico.getTitulo() + " deletado com sucesso");
		}
		
		System.out.println("");
		System.out.println("-------------------------------------");
		System.out.println("");
		
		boolean exists = periodicoRepository.exist(periodico);
		System.out.println(exists);
	}
}
