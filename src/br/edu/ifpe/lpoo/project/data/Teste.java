package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.data.implement.PesquisadorRepository;
import br.edu.ifpe.lpoo.project.entities.membros.Pesquisador;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;

public class Teste {

	public static void main(String[] args) {

		IPesquisadorRepository pesquisadorRepository = new PesquisadorRepository();
		
		Pesquisador pesquisador = new Pesquisador("Eduardo Henrique", "eduardo@email.com", "99988877712", "005800", TipoMembro.PESQUISADOR, 0, StatusMembro.ATIVO, "IFPE");
		
		boolean exist = pesquisadorRepository.existMembro(pesquisador);
		
		System.out.println(exist);
		System.out.println("--------------------------");
		
		if(!exist) {
			pesquisadorRepository.insert(pesquisador);
			System.out.println("Pesquisador cadastrado");
		}else {
			exist = pesquisadorRepository.existMembro(pesquisador);
			
			System.out.println(exist);
			System.out.println("Pesquisador já existe");
		}
	}

}
