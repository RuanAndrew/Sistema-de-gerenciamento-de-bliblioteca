package br.edu.ifpe.lpoo.project.data.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		boolean exists = false;
		
		if(pesquisador == null) {
			return false;
		}
		
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
	public Pesquisador buscarPorId(Integer idMembro) {
		
		if(idMembro == null) {
			throw new DbException("Id inválido");
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
