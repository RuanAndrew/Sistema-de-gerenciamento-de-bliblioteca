package br.edu.ifpe.lpoo.project.data.membros.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.membros.repository.IAlunoRepository;
import br.edu.ifpe.lpoo.project.entities.membros.Aluno;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class AlunoRepository implements IAlunoRepository {

	private Aluno instanciarAluno(ResultSet rst) throws SQLException {

		int idAluno = rst.getInt("id_membro");
		String nome = rst.getString("nome");
		String email = rst.getString("email");
		String cpf = rst.getString("cpf");
		String matricula = rst.getString("matricula");
		String tipo = rst.getString("tipo_membro").toUpperCase();
		TipoMembro tipoMembro = TipoMembro.valueOf(tipo);
		int debitoMultas = rst.getInt("debito_multas");
		String status = rst.getString("status_membro");
		StatusMembro statusMembro = StatusMembro.valueOf(status);
		String curso = rst.getString("curso");

		Aluno aluno = new Aluno(nome, email, cpf, matricula, tipoMembro, debitoMultas, statusMembro, curso);
		aluno.setId(idAluno);

		return aluno;
	}

	@Override
	public void insert(Aluno aluno) {

		if (aluno == null) {
			throw new DbException("Objeto tipo Aluno não pode ser null");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		ResultSet rst = null;

		String sqlMembro = "INSERT INTO membro (nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		String sqlAluno = "INSERT INTO aluno (id_aluno, curso) VALUES (?, ?)";

		int idAluno = -1;

		try {

			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlMembro, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, aluno.getNome());
			stmt.setString(2, aluno.getEmail());
			stmt.setString(3, aluno.getCpf());
			stmt.setString(4, aluno.getMatricula());
			stmt.setString(5, aluno.getTipomembro().name());
			stmt.setInt(6, aluno.getDebitomultas());
			stmt.setString(7, aluno.getStatusmembro().name());
			stmt.executeUpdate();

			rst = stmt.getGeneratedKeys();

			if (rst.next()) {
				idAluno = rst.getInt(1);
				aluno.setId(idAluno);
			} else {
				throw new DbException("Erro ao gerar id para membro");
			}

			stmt1 = conn.prepareStatement(sqlAluno);
			stmt1.setInt(1, idAluno);
			stmt1.setString(2, aluno.getCurso());
			stmt1.executeUpdate();

			conn.commit();

		} catch (SQLException e) {

			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DbException("Erro no rollback ao inserir aluno. Causado por: " + e1.getMessage());
			}
			throw new DbException("Erro ao inserir um novo aluno: " + e.getMessage());
		} finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt1);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}

	@Override
	public boolean existMembro(Aluno aluno) {

		if (aluno == null) {
			throw new DbException("Objeto tipo Aluno não pode ser null");
		}

		boolean exists = false;

		String sqlMembro = "SELECT * FROM membro WHERE cpf = ?";

		try (Connection conn = ConnectionDb.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sqlMembro)) {
			stmt.setString(1, aluno.getCpf());

			try (ResultSet rst = stmt.executeQuery()) {

				exists = rst.next();
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao verificar existência do membro. Causado por: " + e.getMessage());
		}

		return exists;
	}

	public boolean existMembro(String cpf) {

		if (cpf == null) {
			throw new DbException("cpf não pode ser null");
		}

		boolean exists = false;

		String sqlMembro = "SELECT 1 FROM membro WHERE cpf = ?";

		try (Connection conn = ConnectionDb.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sqlMembro)) {
			stmt.setString(1, cpf);

			try (ResultSet rst = stmt.executeQuery()) {

				exists = rst.next();
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao verificar existência do membro. Causado por: " + e.getMessage());
		}

		return exists;
	}

	@Override
	public void delete(int idMembro) {

		if (idMembro <= 0) {
			throw new DbException("Id inválido");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;

		String sqlAluno = "DELETE FROM aluno WHERE id_aluno = ?";

		String sqlMembro = "DELETE FROM membro WHERE id_membro = ?";

		try {

			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlAluno);
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
				throw new DbException("Erro no rollbak ao deletar aluno. Causado por: " + e.getMessage());
			}

			throw new DbException("Erro ao deletar aluno. Causado por: " + e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt1);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}

	@Override
	public Aluno buscarPorId(int idMembro) {

		if (idMembro <= 0) {
			throw new DbException("Id inválido");
		}

		Aluno aluno = null;

		String sqlAluno = "SELECT id_membro, nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, curso FROM membro "
				+ "INNER JOIN aluno ON membro.id_membro = aluno.id_aluno " + "WHERE id_membro = ?";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlAluno)) {

			stmt.setInt(1, idMembro);

			try (ResultSet rst = stmt.executeQuery()) {

				if (rst.next()) {
					aluno = instanciarAluno(rst);
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao busca aluno por id. Causado por: " + e.getMessage());
		}

		return aluno;
	}

	@Override
	public Aluno buscarPorCPF(String cpf) {

		if (cpf == null) {
			throw new DbException("cpf inválido");
		}

		Aluno aluno = null;

		String sqlAluno = "SELECT id_membro, nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, curso " +
				"FROM membro INNER JOIN aluno ON membro.id_membro = aluno.id_aluno " +
				"WHERE cpf = ?";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlAluno)) {

			stmt.setString(1, cpf);

			try (ResultSet rst = stmt.executeQuery()) {

				if (rst.next()) {
					aluno = instanciarAluno(rst);
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao busca aluno por cpf. Causado por: " + e.getMessage());
		}

		return aluno;
	}

	@Override
	public List<Aluno> buscarTodos() {

		List<Aluno> alunos = new ArrayList<Aluno>();

		String sqlAluno = "SELECT id_membro, nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, curso FROM membro "
				+ "INNER JOIN aluno ON membro.id_membro = aluno.id_aluno " + "ORDER BY nome";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlAluno)) {

			try (ResultSet rst = stmt.executeQuery()) {

				while (rst.next()) {
					alunos.add(instanciarAluno(rst));
				}
			}
		} catch (SQLException e) {
			throw new DbException("Erro ao buscar todos aluno. Causado por: " + e.getMessage());
		}

		return alunos;
	}

	@Override
	public List<Aluno> buscarPorTermo(String termo) {

		if (termo == null) {
			throw new DbException("O termo de pesquisa não pode ser null");
		}

		List<Aluno> alunos = new ArrayList<Aluno>();

		String termoStmt = "%" + termo.toLowerCase() + "%";

		String sqlAluno = "SELECT id_membro, nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, curso FROM membro "
				+ "INNER JOIN aluno ON membro.id_membro = aluno.id_aluno "
				+ "WHERE LOWER(nome) LIKE ? OR LOWER(cpf) LIKE ? OR LOWER(matricula) LIKE ? " + "ORDER BY nome";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlAluno)) {

			stmt.setString(1, termoStmt);
			stmt.setString(2, termoStmt);
			stmt.setString(3, termoStmt);

			try (ResultSet rst = stmt.executeQuery()) {

				while (rst.next()) {
					alunos.add(instanciarAluno(rst));
				}
			}
		} catch (SQLException e) {
			throw new DbException("Erro ao buscar aluno por termo. Causado por: " + e.getMessage());
		}

		return alunos;
	}

	@Override
	public void atualizar(Aluno aluno) {

		if (aluno == null) {
			throw new DbException("Aluno não pode ser null");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;

		String sqlMembro = "UPDATE membro "
				+ "SET nome = ?, email = ?, cpf = ?, matricula = ?, tipo_membro = ?, debito_multas = ?, status_membro = ?"
				+ "WHERE id_membro = ?";

		String sqlAluno = "UPDATE aluno SET curso = ? WHERE id_aluno = ?";

		try {
			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlMembro);
			stmt.setString(1, aluno.getNome());
			stmt.setString(2, aluno.getEmail());
			stmt.setString(3, aluno.getCpf());
			stmt.setString(4, aluno.getMatricula());
			stmt.setString(5, aluno.getTipomembro().name());
			stmt.setInt(6, aluno.getDebitomultas());
			stmt.setString(7, aluno.getStatusmembro().name());
			stmt.setInt(8, aluno.getId());
			stmt.executeUpdate();

			stmt1 = conn.prepareStatement(sqlAluno);
			stmt1.setString(1, aluno.getCurso());
			stmt1.setInt(2, aluno.getId());
			stmt1.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DbException("Erro no rollback ao atualizar aluno. Causado por: " + e1.getMessage());
			}
			throw new DbException("Erro ao atualizar aluno. Causado por: " + e.getMessage());

		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeStatement(stmt1);
			ConnectionDb.closeConnection(conn);
		}
	}

}
