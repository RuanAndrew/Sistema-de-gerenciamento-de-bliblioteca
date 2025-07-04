package br.edu.ifpe.lpoo.project.data.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.IExemplarRepository;
import br.edu.ifpe.lpoo.project.data.ILivroRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Livro;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class LivroRepository implements ILivroRepository{
	
	private Livro instanciarLivro(ResultSet rst) throws SQLException{
		
		int idLivro = rst.getInt("id_livro");
		String titulo = rst.getString("titulo");
		String autor = rst.getString("autor");
		int anoPublicacao = rst.getInt("ano_publicacao");
		String editora = rst.getString("editora");
		String idioma = rst.getString("idioma");
		String isbn = rst.getString("isbn");
		int numeroPaginas = rst.getInt("numero_paginas");
		String genero = rst.getString("genero");
		
		Livro livro = new Livro(titulo, autor, anoPublicacao, editora, idioma, isbn, numeroPaginas, genero);
		livro.setId(idLivro);
		
		return livro;
	}
	
	@Override
	public void insert(Livro livro) {
		
		if(livro == null) {
			throw new DbException("Objeto tipo Livro não pode ser null");
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		int idItem = -1;
		
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
			ConnectionDb.closeConnection(conn);
		}
		
		//return idItem;
	}

	@Override
	public boolean existItem(Livro livro) {
		
		if(livro == null) {
			throw new DbException("Objeto tipo Livro não pode ser null");
		}
		
		boolean exists = false;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		try {
			conn = ConnectionDb.getConnection();
			
			stmt = conn.prepareStatement("SELECT * FROM livro WHERE isbn = ? OR (titulo = ? AND editora = ?)");
			
			stmt.setString(1, livro.getIsbn());
			stmt.setString(2, livro.getTitulo());
			stmt.setString(3, livro.getEditora());
			
			rst = stmt.executeQuery();
			
			while(rst.next()) {
				exists = true;
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		
		return exists;
	}
	
	@Override
	public Livro buscarPorId(int idItem) {
		
		if(idItem <= 0) {
			throw new DbException("O id tem que ser tipo inteiro e maior que zero");
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		Livro livro = null;
		
		String consulta = "SELECT * FROM livro WHERE id_livro = ?";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setInt(1, idItem);
			
			rst = stmt.executeQuery();
			
			if(rst.next()) {
				
				livro = instanciarLivro(rst);
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		
		return livro;
	}
	
	@Override
	public List<Livro> buscarTodos(){
		
		List<Livro> livros = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String consulta = "SELECT * FROM livro";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			rst = stmt.executeQuery();
			
			while(rst.next()) {
				livros.add(instanciarLivro(rst));
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		
		return livros;
	}
	
	public List<Livro> buscarPorTermo(String termo){
		
		if(termo == null) {
			throw new DbException("O termo de pesquisa não pode ser null");
		}
		
		List<Livro> livros = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String termoBusca = "%" + termo.toLowerCase() + "%";
		
		String consulta = "SELECT * FROM livro WHERE "
						+ "LOWER(titulo) LIKE ? "
						+ "OR LOWER(isbn) LIKE ? "
						+ "OR LOWER(autor) LIKE ? "
						+ "ORDER BY titulo";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setString(1, termoBusca);
			stmt.setString(2, termoBusca);
			stmt.setString(3, termoBusca);
			
			rst = stmt.executeQuery();
			
			while(rst.next()) {
				
				livros.add(instanciarLivro(rst));
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeConnection(conn);
		}
		
		return livros;
	}
	
	public void delete(int idItem) {
		
		if(idItem <= 0) {
			throw new DbException("O id tem que ser tipo inteiro e maior que zero");
		}
		
		IExemplarRepository exemplarRepository = new ExemplarRepository();
		
		exemplarRepository.deleteParaLivros(idItem);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String consulta = "DELETE FROM livro WHERE id_livro = ?";
		
		try {
			
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setInt(1, idItem);
			
			stmt.executeUpdate();
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}

	@Override
	public void atualizar(Livro livro) {
		
		if(livro == null) {
			throw new DbException("Objeto tipo Livro não pode ser null");
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String consulta = "UPDATE livro "
						+ "SET isbn = ?, numero_paginas = ?, genero = ?, titulo = ?, autor = ?, ano_publicacao = ?, editora = ?, idioma = ? "
						+ "WHERE id_livro = ?";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setString(1, livro.getIsbn());
			stmt.setInt(2, livro.getNumeroPaginas());
			stmt.setString(3, livro.getGenero());
			stmt.setString(4, livro.getTitulo());
			stmt.setString(5, livro.getAutor());
			stmt.setInt(6, livro.getAnoPublicacao());
			stmt.setString(7, livro.getEditora());
			stmt.setString(8, livro.getIdioma());
			stmt.setInt(9, livro.getId());
			
			stmt.executeUpdate();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}	
		
	}
	
}
