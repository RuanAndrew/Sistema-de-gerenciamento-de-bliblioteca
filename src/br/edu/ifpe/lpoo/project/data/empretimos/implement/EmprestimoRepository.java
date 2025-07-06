package br.edu.ifpe.lpoo.project.data.empretimos.implement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.empretimos.repository.IEmprestimoRepository;
import br.edu.ifpe.lpoo.project.entities.emprestimo.Emprestimo;
import br.edu.ifpe.lpoo.project.enums.TipoItemAcervo;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class EmprestimoRepository implements IEmprestimoRepository {

	@Override
	public int insert(Emprestimo emprestimo) {

		if (emprestimo == null) {
			throw new DbException("Objeto tipo Emprestimo não pode ser null");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;

		int idEmprestimo = -1;

		String consulta = "INSERT INTO emprestimo (id_item, id_membro, tipo_item, tipo_membro, data_emprestimo, data_para_devolucao, data_devolucao) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, emprestimo.getIdItemAcervo());
			stmt.setInt(2, emprestimo.getIdMembro());
			stmt.setString(3, emprestimo.getTipoItemAcervo().name());
			stmt.setString(4, emprestimo.getTipoMembro().name());
			stmt.setDate(5, Date.valueOf(emprestimo.getDataEmprestimo()));
			stmt.setDate(6, Date.valueOf(emprestimo.getDataParaDevolucao()));
			stmt.setDate(7, null);

			stmt.executeUpdate();

			rst = stmt.getGeneratedKeys();

			while (rst.next()) {
				idEmprestimo = rst.getInt(1);
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		return idEmprestimo;
	}

	@Override
	public void atualizar(Emprestimo emprestimo) {

		if (emprestimo == null) {
			throw new DbException("Objeto tipo Emprestimo não pode ser null");
		}

		Connection conn = null;
		PreparedStatement stmt = null;

		String consulta = "UPDATE emprestimo "
				+ "SET id_item = ?, id_membro = ? , tipo_item = ?, tipo_membro = ?, data_emprestimo = ?, data_para_devolucao = ?, data_devolucao = ? "
				+ "WHERE id_emprestimo = ?";

		try {

			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);

			stmt.setInt(1, emprestimo.getIdItemAcervo());
			stmt.setInt(2, emprestimo.getIdMembro());
			stmt.setString(3, emprestimo.getTipoItemAcervo().name());
			stmt.setString(4, emprestimo.getTipoMembro().name());
			stmt.setDate(5, emprestimo.getDataEmprestimo() != null ? Date.valueOf(emprestimo.getDataEmprestimo()) : null);
			stmt.setDate(6, emprestimo.getDataParaDevolucao() != null ? Date.valueOf(emprestimo.getDataParaDevolucao()) : null);
			stmt.setDate(7, emprestimo.getDataDevolucao() != null ? Date.valueOf(emprestimo.getDataDevolucao()) : null);
			stmt.setInt(8, emprestimo.getIdEmprestimo());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}

	@Override
	public Emprestimo buscarPorId(int idEmprestimo) {

		if (idEmprestimo <= 0) {
			throw new DbException("O id inválido");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;

		Emprestimo emprestimo = null;

		String consulta = "SELECT * FROM emprestimo WHERE id_emprestimo = ?";

		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);

			stmt.setInt(1, idEmprestimo);

			rst = stmt.executeQuery();

			if (rst.next()) {

				int id = rst.getInt("id_emprestimo");
				int idItemAcervo = rst.getInt("id_item");
				int idMembro = rst.getInt("id_membro");
				String itemAcervo = rst.getString("tipo_item").toUpperCase();
				TipoItemAcervo tipoItemAcervo = TipoItemAcervo.valueOf(itemAcervo);
				String membro = rst.getString("tipo_membro");
				TipoMembro tipoMembro = TipoMembro.valueOf(membro);
				LocalDate dataEmprestimo = rst.getDate("data_emprestimo").toLocalDate();
				LocalDate dataParaDevolucao = rst.getDate("data_para_devolucao").toLocalDate();
				Date dataDevolucaoSql = rst.getDate("data_devolucao");
				LocalDate dataDevolucao = dataDevolucaoSql != null ? dataDevolucaoSql.toLocalDate() : null;

				emprestimo = new Emprestimo(idItemAcervo, idMembro, tipoItemAcervo, tipoMembro, dataEmprestimo,
						dataParaDevolucao, dataDevolucao);
				emprestimo.setIdEmprestimo(id);

			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}

		return emprestimo;
	}

}
