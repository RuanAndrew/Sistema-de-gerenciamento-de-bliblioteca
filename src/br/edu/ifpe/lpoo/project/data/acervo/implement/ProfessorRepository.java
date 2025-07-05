package br.edu.ifpe.lpoo.project.data.acervo.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.IProfessorRepository;
import br.edu.ifpe.lpoo.project.entities.membros.Professor;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class ProfessorRepository implements IProfessorRepository {

	private Professor instanciarProfessor(ResultSet rst) throws SQLException {

		int idProfessor = rst.getInt("id_membro");
		String nome = rst.getString("nome");
		String email = rst.getString("email");
		String cpf = rst.getString("cpf");
		String matricula = rst.getString("matricula");
		String tipo = rst.getString("tipo_membro").toUpperCase();
		TipoMembro tipoMembro = TipoMembro.valueOf(tipo);
		int debitoMultas = rst.getInt("debito_multas");
		String status = rst.getString("status_membro");
		StatusMembro statusMembro = StatusMembro.valueOf(status);
		String areaAtuacao = rst.getString("area_atuacao");
		String departamento = rst.getString("departamento");

		Professor professor = new Professor(nome, email, cpf, matricula, tipoMembro, debitoMultas, statusMembro,
				areaAtuacao, departamento);
		professor.setId(idProfessor);

		return professor;

	}

	@Override
	public void insert(Professor professor) {

		if (professor == null) {
			throw new DbException("Objeto tipo Professor não pode ser null");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		ResultSet rst = null;

		String sqlMembro = "INSERT INTO membro (nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		String sqlProfessor = "INSERT INTO professor (id_professor, area_atuacao, departamento) VALUES (?, ?, ?)";

		int idProfessor = -1;

		try {

			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlMembro, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, professor.getNome());
			stmt.setString(2, professor.getEmail());
			stmt.setString(3, professor.getCpf());
			stmt.setString(4, professor.getMatricula());
			stmt.setString(5, professor.getTipomembro().name());
			stmt.setInt(6, professor.getDebitomultas());
			stmt.setString(7, professor.getStatusmembro().name());
			stmt.executeUpdate();

			rst = stmt.getGeneratedKeys();

			if (rst.next()) {
				idProfessor = rst.getInt(1);
				professor.setId(idProfessor);
			} else {
				throw new DbException("Erro ao gerar id para membro");
			}

			stmt1 = conn.prepareStatement(sqlProfessor);
			stmt1.setInt(1, idProfessor);
			stmt1.setString(2, professor.getAreaAtuacao());
			stmt1.setString(3, professor.getDepartamento());
			stmt1.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DbException("Erro no rollback ao inserir professor. Causado por: " + e1.getMessage());
			}
			throw new DbException("Erro ao inserir um novo professor: " + e.getMessage());
		} finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt1);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}

	}

	@Override
	public boolean existMembro(Professor professor) {

		if (professor == null) {
			throw new DbException("Objeto tipo Professor não pode ser null");
		}

		boolean exists = false;

		String sqlMembro = "SELECT * FROM membro WHERE cpf = ?";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlMembro)) {

			stmt.setString(1, professor.getCpf());

			try (ResultSet rst = stmt.executeQuery()) {

				if (rst.next()) {
					exists = true;
				}
			}
		} catch (SQLException e) {
			throw new DbException("Erro ao verificar se o professor existe. Causado por: " + e.getMessage());
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

		String sqlMembro = "DELETE FROM membro WHERE id_membro = ?";
		String sqlProfessor = "DELETE FROM professor WHERE id_professor = ?";

		try {

			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlProfessor);
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
				throw new DbException("Erro no rollback ao deletar professor. Causado por: " + e1.getMessage());
			}
			throw new DbException("Erro ao deletar  professor. Causado por: " + e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeStatement(stmt1);
			ConnectionDb.closeConnection(conn);
		}
	}

	@Override
	public Professor buscarPorId(int idMembro) {

		if (idMembro <= 0) {
			throw new DbException("Id inválido");
		}

		Professor professor = null;

		String sqlProfessor = "SELECT id_membro, nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, area_atuacao, departamento FROM membro "
				+ "INNER JOIN professor ON membro.id_membro = professor.id_professor " + "WHERE id_membro = ?";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlProfessor)) {

			stmt.setInt(1, idMembro);

			try (ResultSet rst = stmt.executeQuery()) {

				if (rst.next()) {
					professor = instanciarProfessor(rst);
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar professor por id. Causado por: " + e.getMessage());
		}

		return professor;
	}

	@Override
	public List<Professor> buscarTodos() {

		List<Professor> professores = new ArrayList<Professor>();

		String sqlProfessor = "SELECT id_membro, nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, area_atuacao, departamento FROM membro "
				+ "INNER JOIN professor ON membro.id_membro = professor.id_professor";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlProfessor)) {

			try (ResultSet rst = stmt.executeQuery()) {

				while (rst.next()) {
					professores.add(instanciarProfessor(rst));
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar todos os professores. Causado por: " + e.getMessage());
		}

		return professores;
	}

	@Override
	public List<Professor> buscarPorTermo(String termo) {

		if (termo == null) {
			throw new DbException("O termo de pesquisa não pode ser null");
		}

		List<Professor> professores = new ArrayList<Professor>();

		String termoStmt = "%" + termo.toLowerCase() + "%";

		String sqlProfessor = "SELECT id_membro, nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, area_atuacao, departamento FROM membro "
				+ "INNER JOIN professor ON membro.id_membro = professor.id_professor "
				+ "WHERE LOWER(nome) LIKE ? OR LOWER(cpf) LIKE ? OR LOWER(matricula) LIKE ? " + "ORDER BY nome";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlProfessor)) {

			stmt.setString(1, termoStmt);
			stmt.setString(2, termoStmt);
			stmt.setString(3, termoStmt);

			try (ResultSet rst = stmt.executeQuery()) {

				while (rst.next()) {
					professores.add(instanciarProfessor(rst));
				}
			}
		} catch (SQLException e) {
			throw new DbException("Erro ao buscar todos os professores por termo. Causado por: " + e.getMessage());
		}

		return professores;
	}

	@Override
	public void atualizar(Professor professor) {

		if (professor == null) {
			throw new DbException("Professor não pode ser null");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;

		String sqlMembro = "UPDATE membro "
				+ "SET nome = ?, email = ?, cpf = ?, matricula = ?, tipo_membro = ?, debito_multas = ?, status_membro = ? "
				+ "WHERE id_membro = ?";

		String sqlProfessor = "UPDATE professor SET area_atuacao = ?, departamento = ? WHERE id_professor = ?";

		try {
			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlMembro);
			stmt.setString(1, professor.getNome());
			stmt.setString(2, professor.getEmail());
			stmt.setString(3, professor.getCpf());
			stmt.setString(4, professor.getMatricula());
			stmt.setString(5, professor.getTipomembro().name());
			stmt.setInt(6, professor.getDebitomultas());
			stmt.setString(7, professor.getStatusmembro().name());
			stmt.setInt(8, professor.getId());
			stmt.executeUpdate();

			stmt1 = conn.prepareStatement(sqlProfessor);
			stmt1.setString(1, professor.getAreaAtuacao());
			stmt1.setString(2, professor.getDepartamento());
			stmt1.setInt(3, professor.getId());
			stmt1.executeUpdate();

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DbException("Erro no rollback ao atualizar professor. Causado por: " + e1.getMessage());
			}
			throw new DbException("Erro ao atualizar professor: " + e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeStatement(stmt1);
			ConnectionDb.closeConnection(conn);
		}

	}
}
