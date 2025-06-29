package br.edu.ifpe.lpoo.project.data.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.IEbookRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Ebook;
import br.edu.ifpe.lpoo.project.entities.acervo.ItemAcervo;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class EbookRepository implements IEbookRepository{

	@Override
	public void insert(ItemAcervo item) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		Ebook ebook = (Ebook) item;
		
		String consulta = "INSERT INTO ebook (isbn, numero_paginas, genero, titulo, autor, ano_publicacao, editora, idioma, formato_digital, url_ebook)"
							+"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setString(1, ebook.getIsbn());
			stmt.setInt(2, ebook.getNumeroPaginas());
			stmt.setString(3, ebook.getGenero());
			stmt.setString(4, ebook.getTitulo());
			stmt.setString(5, ebook.getAutor());
			stmt.setInt(6, ebook.getAnoPublicacao());
			stmt.setString(7, ebook.getEditora());
			stmt.setString(8, ebook.getIdioma());
			stmt.setString(9, ebook.getFormatoDigital().name());
			stmt.setString(10, ebook.getUrl());
			
			stmt.executeUpdate();
			
			
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}

	@Override
	public boolean exist(ItemAcervo item) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		boolean exists = false;
		
		Ebook ebook = (Ebook) item;
		
		String consulta = "SELECT * FROM ebook "
							+ "WHERE isbn = ? OR (titulo = ? AND autor = ? AND editora = ?)";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setString(1, ebook.getIsbn());
			stmt.setString(2, ebook.getTitulo());
			stmt.setString(3, ebook.getAutor());
			stmt.setString(4, ebook.getEditora());
			
			rst = stmt.executeQuery();
			while(rst.next()) {
				exists = true;
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeConnection(conn);
		}
		return exists;
	}
	
}
