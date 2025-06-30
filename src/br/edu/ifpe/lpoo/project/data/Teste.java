package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.data.implement.AlunoRepository;
import br.edu.ifpe.lpoo.project.entities.membros.Aluno;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;

public class Teste {

	public static void main(String[] args) {
		
		IAlunoRepository alunoRepository = new AlunoRepository();
		
		Aluno aluno = alunoRepository.buscarPorId(4);
		
		
		Aluno aluno1 = new Aluno("Thiago Souza", "thiago.souza@aluno.edu", "32165498700", "A202501", TipoMembro.ALUNO, 0, StatusMembro.ATIVO, "Engenharia Mecânica");
		aluno1.setId(aluno.getId());
		
		
		if(alunoRepository.existMembro(aluno)) {
			
			alunoRepository.atualizar(aluno1);
			System.out.println("Atualizado");
		}else {
			System.out.println("Não existe para atualizar");
		}
		
	}

}
