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
import br.edu.ifpe.lpoo.project.entities.acervo.Exemplar;
import br.edu.ifpe.lpoo.project.enums.StatusExemplar;
import br.edu.ifpe.lpoo.project.enums.TipoExemplar;
import br.edu.ifpe.lpoo.project.enums.TipoItemAcervo;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class ExemplarRepository implements IExemplarRepository {

	private Exemplar instanciarExemplar(ResultSet rst) throws SQLException {

		int idExemplar = rst.getInt("id_exemplar");
		int idLivro = rst.getInt("id_livro");
		String registro = rst.getString("registro");
		String tipoItem = rst.getString("tipo_item_acervo");
		TipoItemAcervo tipoItemAcervo = TipoItemAcervo.valueOf(tipoItem);
		String status = rst.getString("status_exemplar").toUpperCase();
		StatusExemplar statusExemplar = StatusExemplar.valueOf(status);
		String tipo = rst.getString("tipo_exemplar").toUpperCase();
		TipoExemplar tipoExemplar = TipoExemplar.valueOf(tipo);

		Exemplar exemplar = new Exemplar(idLivro, tipoItemAcervo, registro, statusExemplar, tipoExemplar);
		exemplar.setIdExemplar(idExemplar);

		return exemplar;
	}

	@Override
	public void insert(Exemplar exemplar, int idLivro) {

		if (exemplar == null || idLivro <= 0) {
			throw new DbException("Para inserir é necessário Exemplar não nulo e id maior que zero");
		}

		String sqlItemAcervo = "INSERT INTO item_acervo (tipo_item, disponibilidade) VALUES (?, ?)";
		String sqlExemplar = "INSERT INTO exemplar (id_exemplar, id_livro, tipo_item_acervo, registro, status_exemplar, tipo_exemplar) "
				+ "VALUES (?, ?, ?, ?, ?,?)";

		int idItem = -1;

		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		ResultSet rst = null;
		try {

			conn = ConnectionDb.getConnection();

			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlItemAcervo, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, "Exemplar");
			stmt.setString(2, exemplar.getStatus().name());

			stmt.executeUpdate();

			rst = stmt.getGeneratedKeys();

			if (rst.next()) {
				idItem = rst.getInt(1);
			} else {
				throw new DbException("Erro ao criar Id do item");
			}

			stmt1 = conn.prepareStatement(sqlExemplar);
			stmt1.setInt(1, idItem);
			stmt1.setInt(2, idLivro);
			stmt1.setString(3, exemplar.getTipoItemAcervo().name());
			stmt1.setString(4, exemplar.getRegistro());
			stmt1.setString(5, exemplar.getStatus().name());
			stmt1.setString(6, exemplar.getTipoExemplar().name());

			stmt1.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DbException("Erro no rollback. Causado por: " + e1.getMessage());
			}

			throw new DbException("Erro ao registar exemplar no banco de dados. Causado por: " + e.getMessage());

		} finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt1);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}

	@Override
	public boolean existItem(Exemplar exemplar) {

		if (exemplar == null) {
			throw new DbException("Objeto tipo Exemplar não pode ser null");
		}

		boolean exists = false;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;

		String consulta = "SELECT * FROM exemplar WHERE id_exemplar = ? AND registro = ? AND id_livro = ?";

		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);

			stmt.setInt(1, exemplar.getIdExemplar());
			stmt.setString(2, exemplar.getRegistro());
			stmt.setInt(3, exemplar.getIdItem());

			rst = stmt.executeQuery();

			if(rst.next()) {
				exists = true;
			}

		} catch (SQLException e) {
			throw new DbException("Erro na busca a existência do exemplar. Causado por: " + e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeConnection(conn);
		}
		return exists;
	}

	@Override
	public void deleteParaLivros(int idLivro, Connection conn) {

		if (idLivro <= 0) {
			throw new DbException("Id Inválido para deletar exemplares com livros");
		}

		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;

		List<Exemplar> exemplares = buscarTodosPorIdLivro(idLivro, conn);

		String sqlExemplar = "DELETE FROM exemplar WHERE id_livro = ?";
		String sqlItemAcervo = "DELETE FROM item_acervo WHERE id_item = ?";

		try {

			stmt = conn.prepareStatement(sqlExemplar);

			stmt.setInt(1, idLivro);

			stmt.executeUpdate();

			stmt1 = conn.prepareStatement(sqlItemAcervo);

			for (Exemplar exemplar : exemplares) {
				stmt1.setInt(1, exemplar.getIdExemplar());
				stmt1.executeUpdate();
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao deletar exemplares junto com livros. Causado por: " + e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeStatement(stmt1);
		}
	}

	@Override
	public void atualizarStatus(Exemplar exemplar) {

		if (exemplar == null) {
			throw new DbException("Objeto tipo Exemplar não pode ser null para atualizar status");
		}

		String sqlItemAcervo = "UPDATE exemplar SET status_exemplar = ? WHERE id_exemplar = ?";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlItemAcervo)) {

			stmt.setString(1, exemplar.getStatus().name());
			stmt.setInt(2, exemplar.getIdExemplar());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(
					"Erro ao atualizar ao atualizar exemplar no banco de dados. Causado por: " + e.getMessage());
		}
	}

	@Override
	public Exemplar buscarPorId(int idItem) {

		if (idItem <= 0) {
			throw new DbException("Id inválido");
		}

		String sqlExemplarItemAcervo = "SELECT * FROM exemplar WHERE id_exemplar = ?";

		Exemplar exemplar = null;

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlExemplarItemAcervo)) {

			stmt.setInt(1, idItem);

			try (ResultSet rst = stmt.executeQuery()) {

				if (rst.next()) {

					exemplar = instanciarExemplar(rst);
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro na busca de exemplar por id. Causado por: " + e.getMessage());
		}

		return exemplar;
	}

	@Override
	public List<Exemplar> buscarTodosPorIdLivro(int idItem) {

		if (idItem <= 0) {
			throw new DbException("Id inválido para busca de todos os livros");
		}

		String sqlExemplarItemAcervo = "SELECT id_exemplar, id_livro, tipo_item_acervo, registro, status_exemplar, tipo_exemplar FROM exemplar "
				+"WHERE id_livro = ?";

		List<Exemplar> exemplares = new ArrayList<Exemplar>();

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlExemplarItemAcervo)) {

			stmt.setInt(1, idItem);

			try (ResultSet rst = stmt.executeQuery()) {
				while (rst.next()) {
					exemplares.add(instanciarExemplar(rst));
				}
			}

		} catch (SQLException e) {
			throw new DbException(
					"Erro ao buscar exemplares para o livro id " + idItem + ". Causado por: " + e.getMessage());
		}

		return exemplares;
	}

	private List<Exemplar> buscarTodosPorIdLivro(int idItem, Connection conn) {

		if (idItem <= 0) {
			throw new DbException("Id inválido");
		}

		String sqlExemplarItemAcervo = "SELECT id_exemplar, id_livro, tipo_item_acervo, registro, status_exemplar, tipo_exemplar FROM exemplar "
				+ "WHERE id_livro = ?";

		PreparedStatement stmt = null;
		ResultSet rst = null;

		List<Exemplar> exemplares = new ArrayList<Exemplar>();

		try {

			stmt = conn.prepareStatement(sqlExemplarItemAcervo);

			stmt.setInt(1, idItem);

			rst = stmt.executeQuery();

			while (rst.next()) {
				exemplares.add(instanciarExemplar(rst));
			}
		} catch (SQLException e) {
			throw new DbException("Erro na busca interna de exemplares. Causado por: " + e.getMessage());
		} finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
		}
		return exemplares;
	}
}
