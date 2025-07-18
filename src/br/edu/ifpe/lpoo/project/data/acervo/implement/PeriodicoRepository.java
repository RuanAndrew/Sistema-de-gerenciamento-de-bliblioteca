package br.edu.ifpe.lpoo.project.data.acervo.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.acervo.repository.IPeriodicoRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Periodico;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class PeriodicoRepository implements IPeriodicoRepository {

	private Periodico instanciarPeriodico(ResultSet rst) throws SQLException {

		int idPeriodico = rst.getInt("id_periodico");
		String titulo = rst.getString("titulo");
		String autor = rst.getString("autor");
		int anoPublicacao = rst.getInt("ano_publicacao");
		String editora = rst.getString("editora");
		String idioma = rst.getString("idioma");
		String issn = rst.getString("issn");
		int numeroEdicao = rst.getInt("numero_edicao");
		int volume = rst.getInt("volume");
		String genero = rst.getString("genero");

		Periodico periodico = new Periodico(titulo, autor, anoPublicacao, editora, idioma, issn, numeroEdicao, volume,
				genero);
		periodico.setId(idPeriodico);

		return periodico;
	}

	@Override
	public void insert(Periodico periodico) {

		if (periodico == null) {
			throw new DbException("Objeto tipo Periodico não pode ser null");
		}

		String sqlPeriodico = "INSERT INTO periodico (issn, titulo, autor, numero_edicao, volume, editora, idioma, ano_publicacao, genero) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		int idPeriodico = -1;

		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlPeriodico, Statement.RETURN_GENERATED_KEYS)){

			stmt.setString(1, periodico.getIssn());
			stmt.setString(2, periodico.getTitulo());
			stmt.setString(3, periodico.getAutor());
			stmt.setInt(4, periodico.getNumeroEdicao());
			stmt.setInt(5, periodico.getVolume());
			stmt.setString(6, periodico.getEditora());
			stmt.setString(7, periodico.getIdioma());
			stmt.setInt(8, periodico.getAnoPublicacao());
			stmt.setString(9, periodico.getGenero());
			
			stmt.executeUpdate();
			
			try (ResultSet rst = stmt.getGeneratedKeys()){
				
				if(rst.next()) {
					idPeriodico = rst.getInt(1);
					periodico.setId(idPeriodico);
				} else {
					throw new DbException("Não foi gerado id do periódico.");
				}
				
			}
			
		} catch (SQLException e) {
			
			throw new DbException("Erro ao inserir periódico no banco. Causado por: " + e.getMessage());
		} 
	}

	@Override
	public boolean existPorId(int idPeriodico) {
		
		if (idPeriodico <= 0) {
			throw new DbException("O id tem que ser tipo inteiro e maior que zero");
		}
		
		boolean exists = false;
		
		String sqlPeriodico = "SELECT * FROM periodico WHERE id_periodico = ? LIMIT 1";
		
		try (Connection conn = ConnectionDb.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlPeriodico)){
			
			stmt.setInt(1, idPeriodico);
			
			try (ResultSet rst = stmt.executeQuery()){
				exists = rst.next();
			}
			
		}catch (SQLException e) {
			throw new DbException("Erro ao verificar existência do periódico com id, no banco de dados. Causado por: " + e.getMessage());
		}
		
		return exists;
	}

	@Override
	public boolean existItem(Periodico periodico) {

		if (periodico == null) {
			throw new DbException("Objeto tipo Periodico não pode ser null");
		}

		boolean exist = false;

		String sqlPeriodico = "SELECT * FROM periodico WHERE "
				+ "(issn = ?) OR (titulo = ? AND numero_edicao = ? AND volume = ? AND autor = ?)";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlPeriodico)) {

			stmt.setString(1, periodico.getIssn());
			stmt.setString(2, periodico.getTitulo());
			stmt.setInt(3, periodico.getNumeroEdicao());
			stmt.setInt(4, periodico.getVolume());
			stmt.setString(5, periodico.getAutor());

			try (ResultSet rst = stmt.executeQuery()) {
				exist = rst.next();
			}

		} catch (SQLException e) {
			throw new DbException(
					"Erro ao verificar existência do periódico no banco de dados. Causado por: " + e.getMessage());
		}

		return exist;
	}

	@Override
	public Periodico buscarPorId(int idItem) {

		if (idItem <= 0) {
			throw new DbException("O id tem que ser tipo inteiro e maior que zero");
		}

		Periodico periodico = null;

		String sqlPeriodico = "SELECT id_periodico, issn, titulo, autor, numero_edicao, volume, editora, idioma, ano_publicacao, genero "
				+ "WHERE id_periodico = ?";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlPeriodico)) {

			stmt.setInt(1, idItem);

			try (ResultSet rst = stmt.executeQuery()) {
				if (rst.next()) {
					periodico = instanciarPeriodico(rst);
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar periódico por id. Causado por: " + e.getMessage());
		}

		return periodico;
	}

	@Override
	public List<Periodico> buscarTodos() {

		List<Periodico> periodicos = new ArrayList<>();

		String sqlPeriodico = "SELECT id_periodico, issn, titulo, autor, numero_edicao, volume, editora, idioma, ano_publicacao, genero "
				+ "FROM periodico ORDER BY titulo";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlPeriodico)) {

			try (ResultSet rst = stmt.executeQuery()) {

				while (rst.next()) {
					periodicos.add(instanciarPeriodico(rst));
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar todos os periódicos. Causado por: " + e.getMessage());
		}

		return periodicos;
	}

	@Override
	public List<Periodico> buscarPorTermo(String termo) {

		if (termo == null) {
			throw new DbException("O termo de pesquisa não pode ser null");
		}

		List<Periodico> periodicos = new ArrayList<>();

		String buscaTermo = "%" + termo.toLowerCase() + "%";

		String sqlPeriodico = "SELECT id_periodico, issn, titulo, autor, numero_edicao, volume, editora, idioma, ano_publicacao, genero "
				+ "FROM periodico WHERE LOWER(issn) LIKE ? OR LOWER(titulo) LIKE ? OR LOWER(autor) LIKE ? ORDER BY titulo";

		try (Connection conn = ConnectionDb.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlPeriodico)) {

			stmt.setString(1, buscaTermo);
			stmt.setString(2, buscaTermo);
			stmt.setString(3, buscaTermo);

			try (ResultSet rst = stmt.executeQuery()) {

				while (rst.next()) {
					periodicos.add(instanciarPeriodico(rst));
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar periódicos por termo. Causado por: " + e.getMessage());
		}

		return periodicos;
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
		String sqlPeriodico = "DELETE FROM periodico WHERE id_periodico = ?";

		try {

			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlPeriodico);
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
				throw new DbException("Erro no rollback ao deletar periódico. Causado por: " + e1.getMessage());
			}
			throw new DbException("Erro ao deletar periódico. Causado por: " + e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt1);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}

	@Override
	public void atualizar(Periodico periodico) {

		if (periodico == null) {
			throw new DbException("Objeto tipo Periodico não pode ser null");
		}

		Connection conn = null;
		PreparedStatement stmt = null;


		String sqlPeriodico = "UPDATE periodico "
				+ "SET issn = ?, titulo = ?, autor = ?, numero_edicao = ?, volume = ?, editora = ?, idioma = ?, ano_publicacao = ?, genero = ? "
				+ "WHERE id_periodico = ?";


		try {
			conn = ConnectionDb.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(sqlPeriodico);
			stmt.setString(1, periodico.getIssn());
			stmt.setString(2, periodico.getTitulo());
			stmt.setString(3, periodico.getAutor());
			stmt.setInt(4, periodico.getNumeroEdicao());
			stmt.setInt(5, periodico.getVolume());
			stmt.setString(6, periodico.getEditora());
			stmt.setString(7, periodico.getIdioma());
			stmt.setInt(8, periodico.getAnoPublicacao());
			stmt.setString(9, periodico.getGenero());
			stmt.setInt(10, periodico.getId());
			stmt.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DbException("Erro no rollback ao atualizar periódico. Causado por: " + e1.getMessage());
			}
			throw new DbException("Erro ao atualizar periódico. Causado por: " + e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}
}
