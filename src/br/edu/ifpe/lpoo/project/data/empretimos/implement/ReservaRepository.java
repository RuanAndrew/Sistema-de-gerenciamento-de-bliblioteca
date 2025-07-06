package br.edu.ifpe.lpoo.project.data.empretimos.implement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.empretimos.repository.IReservaRepository;
import br.edu.ifpe.lpoo.project.entities.emprestimo.Reserva;
import br.edu.ifpe.lpoo.project.enums.StatusReserva;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class ReservaRepository implements IReservaRepository {

	private br.edu.ifpe.lpoo.project.entities.emprestimo.Reserva instanciarReserva(ResultSet rst) throws SQLException {

		int idReserva = rst.getInt("id_reserva");
		int idMembro = rst.getInt("id_membro");
		int idItemAcervo = rst.getInt("id_item");
		LocalDate dataReserva = rst.getDate("data_reserva").toLocalDate();
		LocalDate dataExpicacao = rst.getDate("data_expiracao").toLocalDate();
		String status = rst.getString("status_reserva");
		StatusReserva statusReserva = StatusReserva.valueOf(status);

		Reserva reserva = new Reserva(idMembro, idItemAcervo, dataReserva, dataExpicacao, statusReserva);
		reserva.setIdReserva(idReserva);

		return reserva;
	}

	@Override
	public int insert(Reserva reserva) {

		if (reserva == null) {
			throw new DbException("Objeto do tipo Reserva não pode ser null para inserir");

		}

		String sqlReserva = "INSERT INTO resereva (id_membro, id_item, data_reserva, data_expiracao, status_reserva)"
				+ "VALUES (?, ?, ?, ?, ?) ";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;

		int idReserva = -1;

		try {
			conn = ConnectionDb.getConnection();

			stmt = conn.prepareStatement(sqlReserva, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, reserva.getIdItemAcervo());
			stmt.setInt(2, reserva.getIdMembro());
			stmt.setDate(3, Date.valueOf(reserva.getDataReserva()));
			stmt.setDate(4, Date.valueOf(reserva.getDataExpiracaoReserva()));
			stmt.setString(5, reserva.getStatusReserva().name());
			stmt.executeQuery();

			rst = stmt.getGeneratedKeys();

			if (rst.next()) {
				idReserva = rst.getInt(1);
				reserva.setIdReserva(idReserva);
			} else {
				throw new DbException("Erro gerar id para Reserva");
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao inserir nova reserva. Causado por: " + e.getMessage());
		}
		return idReserva;
	}

//	@Override
//	public int insert(Reserva reserva) {
//
//		if (reserva == null) {
//			throw new DbException("Objeto do tipo Reserva não pode ser null para inserir");
//
//		}
//
//		String sqlReserva = "INSERT INTO reserva (id_membro, id_item, data_reserva, data_expiracao, status_reserva)"
//				+ "VALUES (?, ?, ?, ?, ?) ";
//
//		String slqStatusItem = "UPDATE item_acervo SET disponibilidade = ? WHERE id_item = ?";
//
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		PreparedStatement stmt1 = null;
//		ResultSet rst = null;
//
//		int idReserva = -1;
//
//		try {
//			conn = ConnectionDb.getConnection();
//			conn.setAutoCommit(false);
//
//			stmt = conn.prepareStatement(sqlReserva, Statement.RETURN_GENERATED_KEYS);
//			stmt.setInt(1, reserva.getIdMembro());
//			stmt.setInt(2, reserva.getIdItemAcervo());
//			stmt.setDate(3, Date.valueOf(reserva.getDataReserva()));
//			stmt.setDate(4, Date.valueOf(reserva.getDataExpiracaoReserva()));
//			stmt.setString(5, reserva.getStatusReserva().name());
//			stmt.executeUpdate();
//
//			rst = stmt.getGeneratedKeys();
//			if (rst.next()) {
//				idReserva = rst.getInt(1);
//				reserva.setIdReserva(idReserva);
//			} else {
//				throw new DbException("Erro gerar id para Reserva");
//			}
//
//			stmt1 = conn.prepareStatement(slqStatusItem);
//			stmt1.setString(1, StatusExemplar.RESERVADO.name());
//			stmt1.setInt(2, reserva.getIdItemAcervo());
//			stmt1.executeUpdate();
//
//			conn.commit();
//
//		} catch (SQLException e) {
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				throw new DbException("Erro no rollback ao inserir nova reserva. Causado por" + e1.getMessage());
//			}
//			throw new DbException("Erro ao inserir nova reserva. Causado por: " + e.getMessage());
//		} finally {
//			ConnectionDb.closeResultSet(rst);
//			ConnectionDb.closeStatement(stmt);
//			ConnectionDb.closeStatement(stmt1);
//			ConnectionDb.closeConnection(conn);
//		}
//		return idReserva;
//	}

	@Override
	public boolean exist(Reserva reserva) {

		if (reserva == null) {
			throw new DbException("Objeto do tipo Reserva não pode ser null para buscar existência");

		}

		boolean exist = false;

		String sqlReserva = "SELECT * FROM reserva WHERE id_membro = ? AND id_item = ? AND data_reserva = ? AND data_expiracao = ? ";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlReserva)) {

			stmt.setInt(1, reserva.getIdMembro());
			stmt.setInt(2, reserva.getIdItemAcervo());
			stmt.setDate(3, Date.valueOf(reserva.getDataReserva()));
			stmt.setDate(4, Date.valueOf(reserva.getDataExpiracaoReserva()));

			try (ResultSet rst = stmt.executeQuery()) {
				if (rst.next()) {
					exist = true;
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar existência da reserva. Causado por: " + e.getMessage());
		}
		return exist;
	}

	@Override
	public void delete(Reserva reserva) {

		if (reserva == null) {
			throw new DbException("Objeto do tipo Reserva não pode ser null para deletar");
		}

		String sqlReserva = "DELETE FROM reserva WHERE id_reserva = ? AND id_membro = ? AND id_item = ? ";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlReserva)) {

			stmt.setInt(1, reserva.getIdReserva());
			stmt.setInt(2, reserva.getIdMembro());
			stmt.setInt(3, reserva.getIdItemAcervo());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DbException("Erro ao deletar reserva. Causado por: " + e.getMessage());
		}
	}

	@Override
	public Reserva buscarPorId(int idReserva) {

		if (idReserva <= 0) {
			throw new DbException("Id inválido");
		}

		Reserva reserva = null;

		String sqlReserva = "SELECT * FROM reserva WHERE id_reserva = ?";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlReserva)) {

			stmt.setInt(1, idReserva);

			try (ResultSet rst = stmt.executeQuery()) {
				if (rst.next()) {
					reserva = instanciarReserva(rst);
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar reserva por id. Causado por: " + e.getMessage());
		}

		return reserva;
	}

	@Override
	public List<Reserva> buscarTodos() {

		List<Reserva> reservas = new ArrayList<Reserva>();

		String sqlReserva = "SELECT * FROM reserva";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlReserva)) {

			try (ResultSet rst = stmt.executeQuery()) {
				while (rst.next()) {
					reservas.add(instanciarReserva(rst));
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar todas reserva. Causado por: " + e.getMessage());
		}
		return reservas;
	}

	@Override
	public void atualizar(Reserva reserva) {

		if (reserva == null) {
			throw new DbException("Objeto do tipo Reserva não pode ser null para atualizar");
		}

		String sqlReserva = "UPDATE reserva SET status_reserva = ? WHERE id_reserva = ?";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlReserva)) {

			stmt.setString(1, reserva.getStatusReserva().name());
			stmt.setInt(2, reserva.getIdReserva());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DbException("Erro ao atualizar reserva. Causado por: " + e.getMessage());
		}
	}

	@Override
	public void atualizarExpiracao() {

		String sqlReserva = "UPDATE reserva SET status_reserva = ? WHERE data_expiracao < ? ";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlReserva)) {

			LocalDate hoje = LocalDate.now();

			stmt.setString(1, StatusReserva.EXPIRADA.name());
			stmt.setDate(2, Date.valueOf(hoje));
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DbException("Erro ao atualizar expiração das reserva. Causado por: " + e.getMessage());
		}
	}
}
