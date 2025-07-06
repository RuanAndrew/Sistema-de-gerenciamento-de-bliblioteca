package br.edu.ifpe.lpoo.project.data;

import java.util.List;

import br.edu.ifpe.lpoo.project.entities.gerenciamento.Reserva;

public interface IReservaRepository {

	int insert(Reserva reserva);

	boolean exist(Reserva reserva);

	void delete(Reserva reserva);

	Reserva buscarPorId(int idReserva);

	List<Reserva> buscarTodos();

	void atualizar(Reserva reserva);

	void atualizarExpiracao();

}
