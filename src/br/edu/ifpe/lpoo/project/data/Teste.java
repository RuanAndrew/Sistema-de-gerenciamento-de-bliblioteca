package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.data.acervo.implement.ExemplarRepository;

public class Teste {

	public static void main(String[] args) {
		
		ExemplarRepository ex = new ExemplarRepository();
		
		System.out.println(ex.existePorId(7));

	}
}
