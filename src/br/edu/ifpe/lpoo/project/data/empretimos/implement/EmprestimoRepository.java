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
import br.edu.ifpe.lpoo.project.data.empretimos.repository.IEmprestimoRepository;
import br.edu.ifpe.lpoo.project.entities.emprestimo.Emprestimo;
import br.edu.ifpe.lpoo.project.enums.StatusEmprestimo;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class EmprestimoRepository implements IEmprestimoRepository {

	private Emprestimo instanciarEmprestimo(ResultSet rst) throws SQLException {

		int id = rst.getInt("id_emprestimo");
		int idItemAcervo = rst.getInt("id_item");
		int idMembro = rst.getInt("id_membro");
		LocalDate dataEmprestimo = rst.getDate("data_emprestimo").toLocalDate();
		LocalDate dataParaDevolucao = rst.getDate("data_para_devolucao").toLocalDate();
		Date dataDevolucaoSql = rst.getDate("data_devolucao");
		LocalDate dataDevolucao = dataDevolucaoSql != null ? dataDevolucaoSql.toLocalDate() : null;
		String status = rst.getString("status_emprestimo").toUpperCase();
		StatusEmprestimo statusEmprestimo = StatusEmprestimo.valueOf(status);

		Emprestimo emprestimo = new Emprestimo(idItemAcervo, idMembro, dataEmprestimo, dataParaDevolucao, dataDevolucao,
				statusEmprestimo);
		emprestimo.setIdEmprestimo(id);

		return emprestimo;
	}

	@Override
	public int insert(Emprestimo emprestimo) {

		if (emprestimo == null) {
			throw new DbException("Objeto tipo Emprestimo não pode ser null");
		}

		int idEmprestimo = -1;

		String sqlEmprestimo = "INSERT INTO emprestimo (id_item, id_membro, data_emprestimo, data_para_devolucao, data_devolucao, status_emprestimo) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlEmprestimo, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, emprestimo.getIdItemAcervo());
			stmt.setInt(2, emprestimo.getIdMembro());
			stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
			stmt.setDate(4, Date.valueOf(emprestimo.getDataParaDevolucao()));
			stmt.setDate(5, null);
			stmt.setString(6, emprestimo.getStatusEmprestimo().name());
			stmt.executeUpdate();

			try (ResultSet rst = stmt.getGeneratedKeys()) {

				if (rst.next()) {
					idEmprestimo = rst.getInt(1);
				} else {
					throw new DbException("Erro ao gerar id");
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao insrir um novo emprestimo. Causado por: " + e.getMessage());
		}
		return idEmprestimo;
	}

	@Override
	public boolean exist(Emprestimo emprestimo) {

		if (emprestimo == null) {
			throw new DbException("Objeto do tipo Emprestimo não pode ser null para buscar existência");
		}

		boolean exist = false;

		String sqlEmprestimo = "SELECT * FROM emprestimo WHERE id_item = ? AND id_membro = ? AND data_emprestimo = ? AND data_para_devolucao= ? ";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlEmprestimo)) {

			stmt.setInt(1, emprestimo.getIdItemAcervo());
			stmt.setInt(2, emprestimo.getIdMembro());
			stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
			stmt.setDate(4, Date.valueOf(emprestimo.getDataParaDevolucao()));

			try (ResultSet rst = stmt.executeQuery()) {
				if (rst.next()) {
					exist = true;
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar existência da emprestimo. Causado por: " + e.getMessage());
		}
		return exist;

	}

	@Override
	public void delete(Emprestimo emprestimo) {

		if (emprestimo == null) {
			throw new DbException("Objeto do tipo Emprestimo não pode ser null para deletar");
		}

		String sqlEmprestimo = "DELETE FROM emprestimo WHERE id_emprestimo = ? AND id_item = ? AND id_membro = ? ";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlEmprestimo)) {

			stmt.setInt(1, emprestimo.getIdEmprestimo());
			stmt.setInt(2, emprestimo.getIdItemAcervo());
			stmt.setInt(3, emprestimo.getIdMembro());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DbException("Erro ao deletar emprestimo. Causado por: " + e.getMessage());
		}
	}

	@Override
	public Emprestimo buscarPorId(int idEmprestimo) {

		if (idEmprestimo <= 0) {
			throw new DbException("O id inválido");
		}

		Emprestimo emprestimo = null;

		String sqlEmprestimo = "SELECT * FROM emprestimo WHERE id_emprestimo = ?";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlEmprestimo)) {

			stmt.setInt(1, idEmprestimo);

			try (ResultSet rst = stmt.executeQuery()) {

				if (rst.next()) {
					emprestimo = instanciarEmprestimo(rst);
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar empréstimo por id. Causado por: " + e.getMessage());
		}

		return emprestimo;
	}

	@Override
	public List<Emprestimo> buscarTodos() {
		
		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		
		String sqlEmprestimo = "SELECT * FROM emprestimo";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlEmprestimo)) {

			try (ResultSet rst = stmt.executeQuery()) {

				while (rst.next()) {
					emprestimos.add(instanciarEmprestimo(rst));
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar todos empréstimos. Causado por: " + e.getMessage());
		}

		return emprestimos;
	}

	@Override
	public void atualizar(Emprestimo emprestimo) {

		if (emprestimo == null) {
			throw new DbException("Objeto tipo Emprestimo não pode ser null");
		}

		String sqlEmprestimo = "UPDATE emprestimo "
				+ "SET id_item = ?, id_membro = ? , data_emprestimo = ?, data_para_devolucao = ?, data_devolucao = ?, status_emprestimo = ? "
				+ "WHERE id_emprestimo = ?";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlEmprestimo)) {

			stmt.setInt(1, emprestimo.getIdItemAcervo());
			stmt.setInt(2, emprestimo.getIdMembro());
			stmt.setDate(3,
					emprestimo.getDataEmprestimo() != null ? Date.valueOf(emprestimo.getDataEmprestimo()) : null);
			stmt.setDate(4,
					emprestimo.getDataParaDevolucao() != null ? Date.valueOf(emprestimo.getDataParaDevolucao()) : null);
			stmt.setDate(5, emprestimo.getDataDevolucao() != null ? Date.valueOf(emprestimo.getDataDevolucao()) : null);
			stmt.setString(6, emprestimo.getStatusEmprestimo().name());
			stmt.setInt(7, emprestimo.getIdEmprestimo());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DbException("Erro ao atualizar emprestimo. Causado por: " + e.getMessage());
		}
	}
	
	@Override
	public void atualizarAtraso() {
		
		String sqlEmprestimo = "UPDATE emprestimos SET status_emprestimo = ? WHERE data_para_devolucao < ?";
		
		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlEmprestimo)){
			
			LocalDate hoje = LocalDate.now();
			
			stmt.setString(1, StatusEmprestimo.ATRASADO.name());
			stmt.setDate(2, Date.valueOf(hoje));
			stmt.executeUpdate();
			
		}catch (Exception e) {
			throw new DbException("Erro ao atualizar status dos empréstimos. Causado por: " + e.getMessage());
		}

	}
}
