package br.edu.ifpe.lpoo.project.data.membros.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.membros.repository.IPesquisadorRepository;
import br.edu.ifpe.lpoo.project.entities.membros.Aluno;
import br.edu.ifpe.lpoo.project.entities.membros.Pesquisador;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class PesquisadorRepository implements IPesquisadorRepository {

	private Pesquisador instanciarPesquisador(ResultSet rst) throws SQLException {

		int idPesquisador = rst.getInt("id_membro");
		String nome = rst.getString("nome");
		String email = rst.getString("email");
		String cpf = rst.getString("cpf");
		String matricula = rst.getString("matricula");
		String tipo = rst.getString("tipo_membro").toUpperCase();
		TipoMembro tipoMembro = TipoMembro.valueOf(tipo);
		int debitoMultas = rst.getInt("debito_multas");
		String status = rst.getString("status_membro").toUpperCase();
		StatusMembro statusMembro = StatusMembro.valueOf(status);
		String instituicao = rst.getString("instituicao");

		Pesquisador pesquisador = new Pesquisador(nome, email, cpf, matricula, tipoMembro, debitoMultas, statusMembro,
				instituicao);
		pesquisador.setId(idPesquisador);

		return pesquisador;

	}

	@Override
	public void insert(Pesquisador pesquisador) {

		if (pesquisador == null) {
			throw new DbException("Objeto tipo Pesquisador não pode ser null");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		ResultSet rst = null;

		String sqlMembro = "INSERT INTO membro (nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		String sqlPesquisador = "INSERT INTO pesquisador (id_pesquisador, instituicao) VALUES (?, ?)";
		int idPesquisador = -1;

		try {

			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlMembro, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, pesquisador.getNome());
			stmt.setString(2, pesquisador.getEmail());
			stmt.setString(3, pesquisador.getCpf());
			stmt.setString(4, pesquisador.getMatricula());
			stmt.setString(5, pesquisador.getTipomembro().name());
			stmt.setInt(6, pesquisador.getDebitomultas());
			stmt.setString(7, pesquisador.getStatusmembro().name());
			stmt.executeUpdate();

			rst = stmt.getGeneratedKeys();

			if (rst.next()) {
				idPesquisador = rst.getInt(1);
				pesquisador.setId(idPesquisador);
			} else {
				throw new DbException("Erro ao gerar id membro para pesquisador");
			}

			stmt1 = conn.prepareStatement(sqlPesquisador);
			stmt1.setInt(1, idPesquisador);
			stmt1.setString(2, pesquisador.getInstituicao());
			stmt1.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DbException("Erro no rollback ao inserir pesquisador: " + e.getMessage());
			}
		} finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt1);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}

	}

	@Override
	public boolean existMembro(Pesquisador pesquisador) {

		if (pesquisador == null) {
			throw new DbException("Objeto tipo Pesquisador não pode ser null");
		}

		boolean exists = false;

		String consulta = "SELECT * FROM membro WHERE cpf = ?";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(consulta)) {

			stmt.setString(1, pesquisador.getCpf());

			try (ResultSet rst = stmt.executeQuery()) {

				if (rst.next()) {
					exists = true;
				}
			}
		} catch (SQLException e) {
			throw new DbException("Erro ao verificar existência de pesquisador.Causado por:" + e.getMessage());
		}

		return exists;
	}

	@Override
	public void delete(int idMembro) {

		if (idMembro <= 0) {
			throw new BusinessExcepition("Id inválido");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;

		String sqlPesquisador = "DELETE FROM pesquisador WHERE id_pesquisador = ?";
		String sqlMembro = "DELETE FROM membro WHERE id_membro = ?";

		try {

			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlPesquisador);
			stmt.setInt(1, idMembro);
			stmt.executeUpdate();

			stmt1 = conn.prepareStatement(sqlMembro);
			stmt1.setInt(1, idMembro);
			stmt1.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DbException("Erro no rollback ao deletar pesquisador. Causado por: " + e1.getMessage());
			}
			throw new DbException("Erro ao deletar pesquisador. Causado por: " + e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeStatement(stmt1);
			ConnectionDb.closeConnection(conn);
		}
	}

	@Override
	public Pesquisador buscarPorId(int idMembro) {

		if (idMembro <= 0) {
			throw new BusinessExcepition("Id inválido");
		}

		Pesquisador pesquisador = null;

		String sqlPesquisador = "SELECT id_membro, nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, instituicao "
				+ "FROM membro INNER JOIN pesquisador ON membro.id_membro = pesquisador.id_pesquisador "
				+ "WHERE id_membro = ?";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlPesquisador)) {

			stmt.setInt(1, idMembro);

			try (ResultSet rst = stmt.executeQuery()) {

				if (rst.next()) {
					pesquisador = instanciarPesquisador(rst);
				}
			}

		} catch (Exception e) {
			throw new DbException("Erro ao buscar pesquisador por id. Causado por: " + e.getMessage());
		}

		return pesquisador;

	}

	@Override
	public Pesquisador buscarPorCPF(String cpf) {

		if (cpf == null) {
			throw new DbException("cpf inválido");
		}

		Pesquisador pesquisador = null;

		String sqlPesquisador = "SELECT id_membro, nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, instituicao " +
				"FROM membro INNER JOIN pesquisador ON membro.id_membro = pesquisador.id_pesquisador " +
				"WHERE cpf = ?";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlPesquisador)) {

			stmt.setString(1, cpf);

			try (ResultSet rst = stmt.executeQuery()) {

				if (rst.next()) {
					pesquisador = instanciarPesquisador(rst);
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao busca pesquisador por cpf. Causado por: " + e.getMessage());
		}

		return pesquisador;
	}

	@Override
	public List<Pesquisador> buscarTodos() {

		List<Pesquisador> pesquisadores = new ArrayList<Pesquisador>();

		String sql = "SELECT id_membro, nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, instituicao "
				+ "FROM membro INNER JOIN pesquisador ON membro.id_membro = pesquisador.id_pesquisador ";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			try (ResultSet rst = stmt.executeQuery()) {

				while (rst.next()) {
					pesquisadores.add(instanciarPesquisador(rst));
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar todos os pesquisadores. Causado por: " + e.getMessage());
		}

		return pesquisadores;
	}

	@Override
	public List<Pesquisador> buscarPorTermo(String termo) {

		if (termo == null) {
			throw new DbException("O termo de pesquisa não pode ser null");
		}

		List<Pesquisador> pesquisadores = new ArrayList<Pesquisador>();

		String termoStmt = "%" + termo.toLowerCase() + "%";

		String sql = "SELECT id_membro, nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, instituicao "
				+ "FROM membro INNER JOIN pesquisador ON membro.id_membro = pesquisador.id_pesquisador "
				+ "WHERE LOWER(nome) LIKE ? OR LOWER(cpf) LIKE ? OR LOWER(matricula) LIKE ? ORDER BY nome";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, termoStmt);
			stmt.setString(2, termoStmt);
			stmt.setString(3, termoStmt);

			try (ResultSet rst = stmt.executeQuery()) {

				while (rst.next()) {
					pesquisadores.add(instanciarPesquisador(rst));
				}
			}
		} catch (SQLException e) {
			throw new DbException("Erro ao buscar pesquisadores por termo. Causado por: " + e.getMessage());
		}

		return pesquisadores;
	}

	@Override
	public void atualizar(Pesquisador pesquisador) {

		if (pesquisador == null) {
			throw new DbException("Pesquisador não pode ser null");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;

		String sqlMembro = "UPDATE membro "
				+ "SET nome = ?, email = ?, cpf = ?, matricula = ?, tipo_membro = ?, debito_multas = ?, status_membro = ? "
				+ "WHERE id_membro = ?";

		String sqlPesquisador = "UPDATE pesquisador SET instituicao = ? WHERE id_pesquisador = ?";

		try {
			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlMembro);
			stmt.setString(1, pesquisador.getNome());
			stmt.setString(2, pesquisador.getEmail());
			stmt.setString(3, pesquisador.getCpf());
			stmt.setString(4, pesquisador.getMatricula());
			stmt.setString(5, pesquisador.getTipomembro().name());
			stmt.setInt(6, pesquisador.getDebitomultas());
			stmt.setString(7, pesquisador.getStatusmembro().name());
			stmt.setInt(8, pesquisador.getId());
			stmt.executeUpdate();

			stmt1 = conn.prepareStatement(sqlPesquisador);
			stmt1.setString(1, pesquisador.getInstituicao());
			stmt1.setInt(2, pesquisador.getId());
			stmt1.executeUpdate();

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DbException("Erro no rollback ao atualizar pesquisador. Causado por: " + e1.getMessage());
			}
			throw new DbException("Erro ao atualizar pesquisador. Causado por: " + e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeStatement(stmt1);
			ConnectionDb.closeConnection(conn);
		}
	}
}
