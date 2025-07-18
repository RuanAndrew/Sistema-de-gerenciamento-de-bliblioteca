package br.edu.ifpe.lpoo.project.data.acervo.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.acervo.repository.IExemplarRepository;
import br.edu.ifpe.lpoo.project.data.acervo.repository.ILivroRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Livro;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class LivroRepository implements ILivroRepository {

	private Livro instanciarLivro(ResultSet rst) throws SQLException {

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

		if (livro == null) {
			throw new DbException("Objeto tipo Livro não pode ser null");
		}

		int idItem = -1;

		String consultaSql = "INSERT INTO livro (isbn, numero_paginas, genero, titulo, autor, ano_publicacao, editora, idioma)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(consultaSql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, livro.getIsbn());
			stmt.setInt(2, livro.getNumeroPaginas());
			stmt.setString(3, livro.getGenero());
			stmt.setString(4, livro.getTitulo());
			stmt.setString(5, livro.getAutor());
			stmt.setInt(6, livro.getAnoPublicacao());
			stmt.setString(7, livro.getEditora());
			stmt.setString(8, livro.getIdioma());

			stmt.executeUpdate();

			try (ResultSet rst = stmt.getGeneratedKeys()) {

				if (rst.next()) {
					idItem = rst.getInt(1);
					livro.setId(idItem);
				} else {
					throw new DbException("Não foi gerado id do livro.");
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao inserir livro no banco de dados. Causado por: " + e.getMessage());
		}
	}
	
	@Override
	public boolean existPorId(int idLivro) {
		
		if (idLivro <= 0) {
			throw new DbException("O id tem que ser tipo inteiro e maior que zero");
		}
		
		String sqlLivro = "SELECT * FROM livro WHERE id_livro = ? LIMIT 1";
		
		boolean exists = false;
		
		try(Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlLivro)){
			
			stmt.setInt(1, idLivro);
			
			try (ResultSet rst = stmt.executeQuery()){
				
				exists = rst.next();
			}
			
		}catch (SQLException e) {
			throw new DbException("Erro ao verificar existência do livro com id, no banco de dados. Causado por: " + e.getMessage());
		}
		
		return exists;
	}
	
	@Override
	public boolean existItem(Livro livro) {

		if (livro == null) {
			throw new DbException("Objeto tipo Livro não pode ser null");
		}

		boolean exists = false;

		String sqlLivro = "SELECT * FROM livro WHERE isbn = ? OR (titulo = ? AND editora = ?)";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlLivro)) {

			stmt.setString(1, livro.getIsbn());
			stmt.setString(2, livro.getTitulo());
			stmt.setString(3, livro.getEditora());

			try (ResultSet rst = stmt.executeQuery()) {
				if (rst.next()) {
					exists = true;
				}
			}
		} catch (SQLException e) {
			throw new DbException("Erro ao buscar existência do livro. Causado por: " + e.getMessage());
		}

		return exists;
	}

	@Override
	public Livro buscarPorId(int idItem) {

		if (idItem <= 0) {
			throw new DbException("O id tem que ser tipo inteiro e maior que zero");
		}

		Livro livro = null;

		String sqlLivro = "SELECT * FROM livro WHERE id_livro = ?";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlLivro)) {

			stmt.setInt(1, idItem);

			try (ResultSet rst = stmt.executeQuery()) {

				if (rst.next()) {
					livro = instanciarLivro(rst);
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar livro por id. Causado por: " + e.getMessage());
		}

		return livro;
	}

	@Override
	public List<Livro> buscarTodos() {

		List<Livro> livros = new ArrayList<>();

		String sqlLivro = "SELECT * FROM livro";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlLivro)) {

			try (ResultSet rst = stmt.executeQuery()) {
				while (rst.next()) {
					livros.add(instanciarLivro(rst));
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar todos os livros. Causado por: " + e.getMessage());
		}

		return livros;
	}

	@Override
	public List<Livro> buscarPorTermo(String termo) {

		if (termo == null) {
			throw new DbException("O termo de pesquisa não pode ser null");
		}

		List<Livro> livros = new ArrayList<>();

		String termoBusca = "%" + termo.toLowerCase() + "%";

		String sqlLivro = "SELECT * FROM livro WHERE " + "LOWER(titulo) LIKE ? " + "OR LOWER(isbn) LIKE ? "
				+ "OR LOWER(autor) LIKE ? " + "ORDER BY titulo";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlLivro)) {

			stmt.setString(1, termoBusca);
			stmt.setString(2, termoBusca);
			stmt.setString(3, termoBusca);

			try (ResultSet rst = stmt.executeQuery()) {

				while (rst.next()) {
					livros.add(instanciarLivro(rst));
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar todos os livros por termo. Causado por: " + e.getMessage());
		}

		return livros;
	}

	@Override
	public void delete(int idItem) {

		if (idItem <= 0) {
			throw new DbException("Id inválido para operação de deletar");
		}

		Connection conn = null;
		PreparedStatement stmt = null;

		String consulta = "DELETE FROM livro WHERE id_livro = ?";

		IExemplarRepository exemplarRepository = new ExemplarRepository();

		try {

			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			exemplarRepository.deleteParaLivros(idItem, conn);

			stmt = conn.prepareStatement(consulta);
			stmt.setInt(1, idItem);
			stmt.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DbException("Erro no rollback da transação. Causado por: " + e1.getMessage());
			}
			throw new DbException("Erro na transação. Causado por: " + e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}

	@Override
	public void atualizar(Livro livro) {

		if (livro == null) {
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
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}

	}

}
