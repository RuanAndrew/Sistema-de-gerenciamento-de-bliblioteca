package br.edu.ifpe.lpoo.project.data.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.ILivroRepository;
import br.edu.ifpe.lpoo.project.entities.ItemArcevo;
import br.edu.ifpe.lpoo.project.entities.Livro;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class LivroRepository implements ILivroRepository{
	
	@Override
	public void insert(ItemArcevo item) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		int idItem = -1;
		
		Livro livro = (Livro) item;
		
		String consultaSql = "INSERT INTO livro (isbn, numero_paginas, genero, titulo, autor, ano_publicacao, editora, idioma)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consultaSql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, livro.getIsbn());
			stmt.setInt(2, livro.getNumeroPaginas());
			stmt.setString(3, livro.getGenero());
			stmt.setString(4, livro.getTitulo());
			stmt.setString(5, livro.getAutor());
			stmt.setInt(6, livro.getAnoPublicacao());
			stmt.setString(7, livro.getEditora());
			stmt.setString(8, livro.getIdioma());
			
			stmt.executeUpdate();
			
			rst = stmt.getGeneratedKeys();
			while(rst.next()) {
				idItem = rst.getInt(1);
			}
			livro.setId(idItem);
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
		}
		
		//return idItem;
	}

	@Override
	public boolean existItem(String indentifier) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		boolean exists = false;
		
		try {
			conn = ConnectionDb.getConnection();
			
			stmt = conn.prepareStatement("SELECT * FROM livro WHERE isbn = ? OR titulo = ?");
			
			stmt.setString(1, indentifier);
			stmt.setString(2, indentifier);
			
			rst = stmt.executeQuery();
			
			while(rst.next()) {
				exists = true;
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
		}
		
		return exists;
	}
	
}
