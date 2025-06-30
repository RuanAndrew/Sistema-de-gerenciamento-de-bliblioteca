package br.edu.ifpe.lpoo.project.data.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.IPesquisadorRepository;
import br.edu.ifpe.lpoo.project.entities.membros.Pesquisador;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class PesquisadorRepository implements IPesquisadorRepository{

	
	private Pesquisador instanciarPesquisador(ResultSet rst) throws SQLException{
		
		int idPesquisador = rst.getInt("id_pesquisador");
		String nome = rst.getString("nome");
		String email = rst.getString("email");
		String cpf = rst.getString("cpf");
		String matricula = rst.getString("matricula");
		String tipo = rst.getString("tipo_membro").toUpperCase();
		TipoMembro tipoMembro = TipoMembro.valueOf(tipo);
		int debitoMultas = rst.getInt("debito_multas");
		String status = rst.getString("status_membro");
		StatusMembro statusMembro = StatusMembro.valueOf(status);
		String instituicao = rst.getString("instituicao");
		
		Pesquisador pesquisador = new Pesquisador(nome, email, cpf, matricula, tipoMembro, debitoMultas, statusMembro, instituicao);
		pesquisador.setId(idPesquisador);
		
		return pesquisador;
		
	}
	
	@Override
	public void insert(Pesquisador pesquisador) {
		
		if(pesquisador == null) {
			throw new DbException("Objeto tipo Pesquisador não pode ser null");
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String consulta = "INSERT INTO pesquisador (nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, instituicao) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, pesquisador.getNome());
			stmt.setString(2, pesquisador.getEmail());
			stmt.setString(3, pesquisador.getCpf());
			stmt.setString(4, pesquisador.getMatricula());
			stmt.setString(5, pesquisador.getTipomembro().name());
			stmt.setInt(6, pesquisador.getDebitomultas());
			stmt.setString(7, pesquisador.getStatusmembro().name());
			stmt.setString(8, pesquisador.getInstituicao());
			
			stmt.executeUpdate();
			
			rst = stmt.getGeneratedKeys();
			
			if(rst.next()) {
				pesquisador.setId(rst.getInt(1));
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		
	}

	@Override
	public boolean existMembro(Pesquisador pesquisador) {
		
		if(pesquisador == null) {
			throw new DbException("Objeto tipo Pesquisador não pode ser null");
		}
		
		boolean exists = false;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String consulta = "SELECT * FROM pesquisador WHERE cpf = ?";
		
		try {
			
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setString(1, pesquisador.getCpf());
			
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
	public void delete(int idMembro) {
		
		if(idMembro <= 0) {
			throw new BusinessExcepition("Id inválido");
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String consulta = "DELETE FROM pesquisador WHERE id_pesquisador = ?";
		
		try {
			
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setInt(1, idMembro);
			
			stmt.executeUpdate();
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
	}

	@Override
	public Pesquisador buscarPorId(int idMembro) {
		
		if(idMembro <= 0) {
			throw new BusinessExcepition("Id inválido");
		}
		
		Pesquisador pesquisador = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String consulta = "SELECT * FROM pesquisador WHERE id_pesquisador = ?";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setInt(1, idMembro);
			
			rst = stmt.executeQuery();
			
			if(rst.next()) {
				pesquisador = instanciarPesquisador(rst);
			}
			
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
			
		return pesquisador;
		
	}

	@Override
	public List<Pesquisador> buscarTodos() {

		List<Pesquisador> pesquisadores = new ArrayList<Pesquisador>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String consulta = "SELECT * FROM pesquisador";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			rst = stmt.executeQuery();
			
			while(rst.next()) {
				pesquisadores.add(instanciarPesquisador(rst));
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		
		return pesquisadores;
	}

	@Override
	public List<Pesquisador> buscarPorTermo(String termo) {
		
		if(termo == null) {
			throw new DbException("O termo de pesquisa não pode ser null");
		}
		
		List<Pesquisador> pesquisadores = new ArrayList<Pesquisador>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String termoStmt = "%" + termo.toLowerCase() + "%";
		
		String consulta = "SELECT * FROM pesquisador "
				+ "WHERE LOWER(nome) LIKE ? "
				+ "OR LOWER(cpf) LIKE ? "
				+ "OR LOWER(matricula) LIKE ? "
				+ "ORDER BY nome";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setString(1, termoStmt);
			stmt.setString(2, termoStmt);
			stmt.setString(3, termoStmt);
			
			rst = stmt.executeQuery();
			
			while(rst.next()) {
				pesquisadores.add(instanciarPesquisador(rst));
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		return pesquisadores;
	}

//	@Override
//	public void atualizar(Pesquisador pesquisador) {
//		if(pesquisador == null) {
//			throw new DbException("Aluno não pode ser null");
//		}
//		
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		
//		String consulta = "UPDATE pesquisador "
//						+ "SET nome = ?, email = ?, cpf = ?, matricula = ?, tipo_membro = ?, debito_multas = ?, status_membro = ?, instituicao = ?"
//						+ "WHERE id_pesquisador = ?";
//		
//		try {
//			conn = ConnectionDb.getConnection();
//			stmt = conn.prepareStatement(consulta);
//			
//			stmt.setString(1, pesquisador.getNome());
//			stmt.setString(2, pesquisador.getEmail());
//			stmt.setString(3, pesquisador.getCpf());
//			stmt.setString(4, pesquisador.getMatricula());
//			stmt.setString(5, pesquisador.getTipomembro().name());
//			stmt.setInt(6, pesquisador.getDebitomultas());
//			stmt.setString(7, pesquisador.getStatusmembro().name());
//			stmt.setString(8, pesquisador.getInstituicao());
//			stmt.setInt(9, pesquisador.getId());
//			
//			stmt.executeUpdate();
//			
//		} catch (Exception e) {
//			throw new DbException(e.getMessage());
//		}finally {
//			ConnectionDb.closeStatement(stmt);
//			ConnectionDb.closeConnection(conn);
//		}
//		
//	}
}
