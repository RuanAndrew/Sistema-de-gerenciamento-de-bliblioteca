package br.edu.ifpe.lpoo.project.data.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.IExemplarRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Exemplar;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class ExemplarRepository implements IExemplarRepository{

	@Override
	public void insert(Exemplar exemplar, int idLivro) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement("INSERT INTO exemplar (id_livro, registro, status_exemplar)"
										+ "VALUES (?, ?, ?)");
			
			stmt.setInt(1, idLivro);
			stmt.setString(2, exemplar.getRegistro());
			stmt.setString(3, exemplar.isDisponivel().name());
			
			stmt.executeUpdate();
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeStatement(stmt);
		}
		
	}
	
	@Override
	public boolean existItem(String indentifier) {
		
		return false;
	}

	
	@Override
	public void deleteParaLivros(int idLivro) {
		
		if(idLivro <= 0) {
			throw new BusinessExcepition("Não é possível deletar um objeto Livro nulo");
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String consulta = "DELETE FROM exemplar WHERE id_livro = ?";
		
		try {
			
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setInt(1, idLivro);
			
			stmt.executeUpdate();
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}
}
