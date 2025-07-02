package br.edu.ifpe.lpoo.project.data.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.IExemplarRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Exemplar;
import br.edu.ifpe.lpoo.project.enums.StatusExemplar;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class ExemplarRepository implements IExemplarRepository {
	
	private Exemplar instanciarExemplar(ResultSet rst) throws SQLException{
		
		int idExemplar = rst.getInt("id_exemplar");
		int idLivro = rst.getInt("id_livro");
		String registro = rst.getString("registro");
		String status = rst.getString("status_exemplar").toUpperCase();
		StatusExemplar statusExemplar = StatusExemplar.valueOf(status);

		Exemplar exemplar = new Exemplar(idLivro, registro, statusExemplar);
		exemplar.setIdLivro(idExemplar);
		
		return exemplar;
	}
	
	@Override
	public void insert(Exemplar exemplar, int idLivro) {

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(
					"INSERT INTO exemplar (id_livro, registro, status_exemplar)" + "VALUES (?, ?, ?)");

			stmt.setInt(1, idLivro);
			stmt.setString(2, exemplar.getRegistro());
			stmt.setString(3, exemplar.isDisponivel().name());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
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

		String consulta = "SELECT * FROM exemplar WHERE id_livro = ? AND registro = ?";

		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);

			stmt.setInt(1, exemplar.getIdLivro());
			stmt.setString(2, exemplar.getRegistro());

			rst = stmt.executeQuery();
			while (rst.next()) {
				exists = true;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeConnection(conn);
		}
		return exists;
	}

	@Override
	public void atualizarStatus(Exemplar exemplar) {
		Connection conn = null;
		PreparedStatement stmt = null;

		String consulta = "UPDATE exemplar " + "SET status_exemplar = ? " + "WHERE id_exemplar = ?";

		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);

			stmt.setString(1, exemplar.isDisponivel().name());
			stmt.setInt(2, exemplar.getIdExemplar());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}
	
	@Override
	public Exemplar buscarPorId(int idItem) {

		if (idItem <= 0) {
			throw new DbException("Id inválido");
		}

		String consulta = "SELECT * FROM exemplar WHERE id_exemplar = ?";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;

		Exemplar exemplar = null;

		try {

			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);

			stmt.setInt(1, idItem);

			rst = stmt.executeQuery();

			while (rst.next()) {

				exemplar = instanciarExemplar(rst);

			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		return exemplar;
	}

	@Override
	public void deleteParaLivros(int idLivro) {

		if (idLivro <= 0) {
			throw new BusinessExcepition("Não é possível deletar um objeto Livro nulo");
		}

		Connection conn = null;
		PreparedStatement stmt = null;

		String consulta = "DELETE FROM exemplar WHERE id_livro = ?";

		try {

			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);

			stmt.setInt(1, idLivro);

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}

	@Override
	public List<Exemplar> buscarTodosPorIdLivro(int idItem) {

		if (idItem <= 0) {
			throw new DbException("Id inválido");
		}
		
		String consulta = "SELECT * FROM exemplar WHERE id_livro = ?";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;

		List<Exemplar> exemplares = new ArrayList<Exemplar>();
		
		try {

			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);

			stmt.setInt(1, idItem);

			rst = stmt.executeQuery();

			while (rst.next()) {
				
				exemplares.add(instanciarExemplar(rst));

			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		return exemplares;
	}
}
