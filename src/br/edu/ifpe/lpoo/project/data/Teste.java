package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.data.implement.ExemplarRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Exemplar;
import br.edu.ifpe.lpoo.project.enums.StatusExemplar;

public class Teste {

	public static void main(String[] args) {

		IExemplarRepository exemplarRepository = new ExemplarRepository();
		
		Exemplar exemplar = new Exemplar(2, "2EPX2", StatusExemplar.DISPONIVEL);
		exemplar.setIdExemplar(4);
		exemplarRepository.atualizarStatus(exemplar);
		
	}
}
