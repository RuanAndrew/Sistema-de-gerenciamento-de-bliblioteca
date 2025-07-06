package br.edu.ifpe.lpoo.project.data.empretimos.repository;

import br.edu.ifpe.lpoo.project.entities.emprestimo.Emprestimo;

public interface IEmprestimoRepository {

	int insert(Emprestimo emprestimo);

	void atualizar(Emprestimo emprestimo);
	
	Emprestimo buscarPorId(int idEmprestimo);
}
