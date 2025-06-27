package br.edu.ifpe.lpoo.project.data.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.IEbookRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Ebook;
import br.edu.ifpe.lpoo.project.entities.acervo.ItemAcervo;
import br.edu.ifpe.lpoo.project.enums.FormatoDigital;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class EbookRepository implements IEbookRepository{
	
	private Ebook instanciarEbook(ResultSet rst) throws SQLException{
		
		int idEbook = rst.getInt("id_ebook");
		String titulo = rst.getString("titulo");
		String autor = rst.getString("autor");
		int anoPublicacao = rst.getInt("ano_publicacao");
		String editora = rst.getString("editora");
		String idioma = rst.getString("idioma");
		String isbn = rst.getString("isbn");
		int numeroPaginas = rst.getInt("numero_paginas");
		String genero = rst.getString("genero");
		String formato = rst.getString("formato_digital").toUpperCase();
		FormatoDigital formatodigital = FormatoDigital.valueOf(formato);
		String urlEbook = rst.getString("url_ebook");
		
		Ebook ebook = new Ebook(titulo, autor, anoPublicacao, editora, idioma, isbn, numeroPaginas, genero, formatodigital, urlEbook);
		ebook.setId(idEbook);
		
		return ebook;
	}
	
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
		
		boolean exists = false;
		
		if(item == null) {
			return exists;
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
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
	
	@Override
	public Ebook buscarPorId(int idItem) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		Ebook ebook = null;
		
		String consulta = "SELECT * FROM ebook WHERE id_ebook = ?";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setInt(1, idItem);
			
			rst = stmt.executeQuery();
			
			if(rst.next()) {

				ebook = instanciarEbook(rst);
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		
		return ebook;
	}
	
	@Override
	public List<Ebook> buscarTodos(){
		
		List<Ebook> ebooks = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String consulta = "SELECT * FROM ebook";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			rst = stmt.executeQuery();
			
			while(rst.next()) {
				
				ebooks.add(instanciarEbook(rst));
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		
		return ebooks;
	}
	
	
	@Override
	public List<Ebook> buscarPorTermo(String termo){
		
		List<Ebook> ebooks = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String termoBusca = "%" + termo.toLowerCase() + "%";
		
		String consulta = "SELECT * FROM ebook "
							+ "WHERE LOWER(titulo) LIKE ?"
							+ "OR LOWER(autor) LIKE ? "
							+ "OR LOWER(editora) LIKE ? "
							+ "ORDER BY titulo";
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setString(1, termoBusca);
			stmt.setString(2, termoBusca);
			stmt.setString(3, termoBusca);
			
			rst = stmt.executeQuery();
			
			while(rst.next()) {
				
				ebooks.add(instanciarEbook(rst));
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		
		return ebooks;
	}
	
	@Override
	public void delete(Ebook ebook) {
		
		if(ebook == null) {
			throw new BusinessExcepition("Não é possível deletar um objeto Ebook nulo");
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String consulta = "DELETE FROM ebook WHERE id_ebook = ?";
		
		try {
			
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setInt(1, ebook.getId());
			
			stmt.executeUpdate();
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}
}
