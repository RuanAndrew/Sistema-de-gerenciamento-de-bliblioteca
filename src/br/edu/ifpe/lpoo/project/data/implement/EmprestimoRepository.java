package br.edu.ifpe.lpoo.project.data.implement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.IEmprestimoRepository;
import br.edu.ifpe.lpoo.project.entities.gerenciamento.Emprestimo;
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
			
			while(rst.next()) {
				idEmprestimo = rst.getInt(1);
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		return idEmprestimo;
	}

}
