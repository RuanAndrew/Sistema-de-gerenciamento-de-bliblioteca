package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.data.acervo.implement.ExemplarRepository;
import br.edu.ifpe.lpoo.project.enums.TipoItemAcervo;

public class Teste {

	public static void main(String[] args) {
		
		ExemplarRepository ex = new ExemplarRepository();
		
		System.out.println(ex.getMaiorRegistro(1, TipoItemAcervo.LIVRO));

	}
}
