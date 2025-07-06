package br.edu.ifpe.lpoo.project.data.empretimos.repository;

import java.util.List;

import br.edu.ifpe.lpoo.project.entities.emprestimo.Emprestimo;

public interface IEmprestimoRepository {

	int insert(Emprestimo emprestimo);

	boolean exist(Emprestimo emprestimo);

	void delete(Emprestimo emprestimo);

	Emprestimo buscarPorId(int idEmprestimo);

	List<Emprestimo> buscarTodos();

	void atualizar(Emprestimo emprestimo);

	void atualizarAtraso();
}
