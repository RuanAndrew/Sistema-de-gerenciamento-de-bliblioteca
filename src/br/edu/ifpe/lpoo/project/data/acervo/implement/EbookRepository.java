package br.edu.ifpe.lpoo.project.data.acervo.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.acervo.repository.IEbookRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Ebook;
import br.edu.ifpe.lpoo.project.enums.FormatoDigital;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class EbookRepository implements IEbookRepository {

	private Ebook instanciarEbook(ResultSet rst) throws SQLException {

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

		Ebook ebook = new Ebook(titulo, autor, anoPublicacao, editora, idioma, isbn, numeroPaginas, genero,
				formatodigital, urlEbook);
		ebook.setId(idEbook);

		return ebook;
	}

	@Override
	public void insert(Ebook ebook) {

		if (ebook == null) {
			throw new DbException("Objeto tipo Ebook não pode ser null");
		}


		String sqlEbook = "INSERT INTO ebook (isbn, numero_paginas, genero, titulo, autor, ano_publicacao, editora, idioma, formato_digital, url_ebook)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		int idEbook = -1;

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlEbook, Statement.RETURN_GENERATED_KEYS)) {
			
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
			
			try (ResultSet rst = stmt.getGeneratedKeys()){

				if (rst.next()) {
					idEbook = rst.getInt(1);
					ebook.setId(idEbook);
				} else {
					throw new DbException("Erro ao gerar id para o ebook inserido");
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao inserir ebook no banco. Causado por: " + e.getMessage());
		}
	}
	
	@Override
	public boolean existPorId(int idEbook) {
		
		if (idEbook <= 0) {
			throw new DbException("O id tem que ser tipo inteiro e maior que zero");
		}
		
		String sqlEbook = "SELECT * FROM ebook WHERE  id_ebook = ? LIMIT 1";
		
		boolean exists = false;
		
		try(Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlEbook)){
			
			stmt.setInt(1, idEbook);
			try(ResultSet rst = stmt.executeQuery()){
				exists = rst.next();
			}
		}catch (SQLException e) {
			throw new DbException("Erro ao verificar existência do ebook com id, no banco de dados. Causado por: " + e.getMessage());
		}
		return exists;
	}

	@Override
	public boolean existItem(Ebook ebook) {

		if (ebook == null) {
			throw new DbException("Objeto tipo Ebook não pode ser null");
		}

		boolean exists = false;

		String sqlEbook = "SELECT * FROM ebook " + "WHERE (isbn = ?) OR (titulo = ? AND autor = ? AND editora = ?)";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlEbook)) {

			stmt.setString(1, ebook.getIsbn());
			stmt.setString(2, ebook.getTitulo());
			stmt.setString(3, ebook.getAutor());
			stmt.setString(4, ebook.getEditora());

			try (ResultSet rst = stmt.executeQuery()) {
				if (rst.next()) {
					exists = true;
				}
			}

		} catch (SQLException e) {
			throw new DbException(
					"Erro ao verificar existência do ebook no banco de dados. Causado por: " + e.getMessage());
		}

		return exists;
	}

	@Override
	public Ebook buscarPorId(int idItem) {

		if (idItem <= 0) {
			throw new DbException("O id tem que ser tipo inteiro e maior que zero");
		}

		Ebook ebook = null;

		String sqlEbook = "SELECT id_ebook, isbn, numero_paginas, genero, titulo, autor, ano_publicacao, editora, idioma, formato_digital, url_ebook "
				+ "FROM ebook WHERE id_ebook = ?";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlEbook)) {

			stmt.setInt(1, idItem);

			try (ResultSet rst = stmt.executeQuery()) {
				if (rst.next()) {
					ebook = instanciarEbook(rst);
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao ebook buscar  por id. Causado por: " + e.getMessage());
		}

		return ebook;
	}

	@Override
	public List<Ebook> buscarTodos() {

		List<Ebook> ebooks = new ArrayList<>();

		String sqlEbook = "SELECT id_ebook, isbn, numero_paginas, genero, titulo, autor, ano_publicacao, editora, idioma, formato_digital, url_ebook "
				+ "FROM ebook ORDER BY titulo";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlEbook)) {

			try (ResultSet rst = stmt.executeQuery()) {

				while (rst.next()) {
					ebooks.add(instanciarEbook(rst));
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar todos os ebooks. Causado por: " + e.getMessage());
		}

		return ebooks;
	}

	@Override
	public List<Ebook> buscarPorTermo(String termo) {

		if (termo == null) {
			throw new DbException("O termo de pesquisa não pode ser null");
		}

		List<Ebook> ebooks = new ArrayList<>();

		String termoBusca = "%" + termo.toLowerCase() + "%";

		String sqlEbook = "SELECT id_ebook, isbn, numero_paginas, genero, titulo, autor, ano_publicacao, editora, idioma, formato_digital, url_ebook  "
				+ "FROM ebook " + "WHERE LOWER(titulo) LIKE ? OR LOWER(autor) LIKE ? OR LOWER(editora) LIKE ? "
				+ "ORDER BY titulo";

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlEbook)) {

			stmt.setString(1, termoBusca);
			stmt.setString(2, termoBusca);
			stmt.setString(3, termoBusca);

			try (ResultSet rst = stmt.executeQuery()) {
				while (rst.next()) {
					ebooks.add(instanciarEbook(rst));
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar todos os ebooks por termo. Causado por: " + e.getMessage());
		}

		return ebooks;
	}

	@Override
	public void delete(int idItem) {

		if (idItem <= 0) {
			throw new DbException("O id tem que ser tipo inteiro e maior que zero");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;

		String sqlItemAcervo = "DELETE FROM item_acervo WHERE id_item = ?";
		String sqlEbook = "DELETE FROM ebook WHERE id_ebook = ?";

		try {

			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlEbook);
			stmt.setInt(1, idItem);
			stmt.executeUpdate();

			stmt1 = conn.prepareStatement(sqlItemAcervo);
			stmt1.setInt(1, idItem);
			stmt1.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DbException("Erro no rollback ao deletar ebook. Causado por: " + e1.getMessage());
			}
			throw new DbException("Erro ao deletar ebook. Causado por: " + e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt1);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}

	@Override
	public void atualizar(Ebook ebook) {

		if (ebook == null) {
			throw new DbException("Objeto tipo Ebook não pode ser null");
		}

		Connection conn = null;
		PreparedStatement stmt = null;

		// Se inserir status(disponibilidade) na classe Ebook
//		PreparedStatement stmt1 = null;

		String sqlEbook = " UPDATE ebook "
				+ "SET isbn = ?, numero_paginas = ?, genero = ?, titulo = ?, autor = ?, ano_publicacao = ?, "
				+ "editora = ?, idioma = ?, formato_digital  = ?, url_ebook = ? " + "WHERE id_ebook = ?";

		// Se inserir status(disponibilidade) na classe Ebook
//		String sqlItemAcervo = "UPDATE item_acervo SET disponibilidade = ? WHERE id_item = ?";

		try {
			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlEbook);
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
			stmt.setInt(11, ebook.getId());
			stmt.executeUpdate();

			// Se inserir status(disponibilidade) na classe Ebook
//			stmt1 = conn.prepareStatement(sqlItemAcervo);
//			stmt1.setString(1, "Disponível");
//			stmt.setInt(2, ebook.getId());
//			stmt1.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DbException("Erro no rollback ao atualizar ebook. Causado por: " + e1.getMessage());
			}
			throw new DbException("Erro ao atualizar ebook. Causado por: " + e.getMessage());
		} finally {
			// Se inserir status(disponibilidade) na classe Ebook
//			ConnectionDb.closeStatement(stmt1);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}

	}
}
