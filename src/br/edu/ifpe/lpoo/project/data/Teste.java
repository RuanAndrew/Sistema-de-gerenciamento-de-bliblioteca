package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.data.implement.AlunoRepository;
import br.edu.ifpe.lpoo.project.data.implement.PesquisadorRepository;
import br.edu.ifpe.lpoo.project.data.implement.ProfessorRepository;

public class Teste {

	public static void main(String[] args) {

		IAlunoRepository alunoRepository = new AlunoRepository();
		IProfessorRepository professorRepository = new ProfessorRepository();
		IPesquisadorRepository pesquisadorRepository = new PesquisadorRepository();
			
		
	}

}
