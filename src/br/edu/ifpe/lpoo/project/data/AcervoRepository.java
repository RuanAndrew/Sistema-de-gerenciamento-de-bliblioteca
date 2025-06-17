package br.edu.ifpe.lpoo.project.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

<<<<<<< HEAD
import br.edu.ifpe.lpoo.project.entities.ItemArcevo;
import br.edu.ifpe.lpoo.project.entities.Livro;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class AcervoRepository implements IAcervoRepository{

	@Override
	public void insert(ItemArcevo item) {
//		if(!(item instanceof Livro)) {
//			throw new IllegalArgumentException("Item não é livro");
//		}
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		//int idItem = -1;
		
		if(item instanceof Livro) {
			Livro livro = (Livro) item;
			
			try {
				
				conn = ConnectionDb.getConnection();
				stmt = conn.prepareStatement("INSERT INTO livros(isbn, numero_paginas, genero, titulo, autor, ano_publicacao, editora, idioma)"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				
				stmt.setString(1, livro.getIsbn());
				stmt.setInt(2, livro.getNumeroPaginas());
				stmt.setString(3, livro.getGenero());
				stmt.setString(4, livro.getTitulo());
				stmt.setString(5, livro.getAutor());
				stmt.setString(6, livro.getAnoPublicacao());
				stmt.setString(7, livro.getEditora());
				stmt.setString(8, livro.getIdioma());
				
				stmt.executeUpdate();
				
//				int colunasAfetadas = stmt.executeUpdate();
				
//				rst = stmt.getGeneratedKeys();
//				while(rst.next()) {
//					idItem = rst.getInt(1);
//				}
				
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}finally {
				
				ConnectionDb.closeResultSet(rst);
				ConnectionDb.closeStatement(stmt);
				ConnectionDb.closeConnection();
			}
		}
		
		//return idItem;
	}

	@Override
	public boolean hasItem(String indentifier) {
		
//		Connection conn = null;
//		ResultSet rst = null;
//		PreparedStatement stmt = null;
		
=======
public class AcervoRepository implements IAcervoRepository {

	@Override
	public void insert(ItemArcevo item) {
		
		
	}

	@Override
	public boolean existItem(String indentifier) {
>>>>>>> cd0ee23e433f7cdd6a93e415e72760b556db34e8
		
		return false;
	}
	
	
}
