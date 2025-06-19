package br.edu.ifpe.lpoo.project.data.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.IExemplarRepository;
import br.edu.ifpe.lpoo.project.entities.Exemplar;
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
		}
		
	}
	
	@Override
	public boolean existItem(String indentifier) {
		
		return false;
	}

	
}
