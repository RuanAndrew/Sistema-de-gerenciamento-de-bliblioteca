package br.edu.ifpe.lpoo.project.data.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.IAlunoRepository;
import br.edu.ifpe.lpoo.project.entities.membros.Aluno;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class AlunoRepository implements IAlunoRepository{
	
	private Aluno instanciarAluno(ResultSet rst) throws SQLException{
		
		int idAluno = rst.getInt("id_aluno");
		String nome = rst.getString("nome");
		String email = rst.getString("email");
		String cpf = rst.getString("cpf");
		String matricula = rst.getString("matricula");
		String tipo = rst.getString("tipo_membro").toUpperCase();
		TipoMembro tipoMembro = TipoMembro.valueOf(tipo);
		int debitoMultas = rst.getInt("debito_multas");
		String status = rst.getString("status_membro");
		StatusMembro statusMembro = StatusMembro.valueOf(status);
		String curso = rst.getString("curso");
		
		Aluno aluno = new Aluno(nome, email, cpf, matricula, tipoMembro, debitoMultas, statusMembro, curso);
		aluno.setId(idAluno);
		
		return aluno;
		
	}
	
	@Override
	public void insert (Aluno aluno) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;

		String consulta = "INSERT INTO aluno (nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, curso) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, aluno.getNome());
			stmt.setString(2, aluno.getEmail());
			stmt.setString(3, aluno.getCpf());
			stmt.setString(4, aluno.getMatricula());
			stmt.setString(5, aluno.getTipomembro().name());
			stmt.setInt(6, aluno.getDebitomultas());
			stmt.setString(7, aluno.getStatusmembro().name());
			stmt.setString(8, aluno.getCurso());
			
			stmt.executeUpdate();
			
			rst = stmt.getGeneratedKeys();
			
			if(rst.next()) {
				aluno.setId(rst.getInt(1));
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
	public boolean existMembro(Aluno aluno) {
		
		boolean exists = false;
		
		if(aluno == null) {
			return false;
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String consulta = "SELECT * FROM aluno WHERE cpf = ?";
		
		try {
			
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setString(1, aluno.getCpf());
			
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
			throw new DbException("Id inválido");
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String consulta = "DELETE FROM aluno WHERE id_aluno = ?";
		
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
	public Aluno buscarPorId(Integer idMembro) {
		
		if(idMembro == null) {
			throw new DbException("Id inválido");
		}
		
		Aluno aluno = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String consulta = "SELECT * FROM aluno WHERE id_aluno = ?";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setInt(1, idMembro);
			
			rst = stmt.executeQuery();
			
			if(rst.next()) {
				aluno = instanciarAluno(rst);
			}
			
			
			
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
			
		return aluno;
	}

//	@Override
//	public void atualizar(Aluno aluno) {
//		
//		if(aluno == null) {
//			throw new DbException("Aluno não pode ser null");
//		}
//		
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		
//		String consulta = "UPDATE aluno "
//						+ "SET nome = ?, email = ?, cpf = ?, matricula = ?, tipo_membro = ?, debito_multas = ?, status_membro = ?, curso = ?"
//						+ "WHERE id_aluno = ?";
//		
//		try {
//			conn = ConnectionDb.getConnection();
//			stmt = conn.prepareStatement(consulta);
//			
//			stmt.setString(1, aluno.getNome());
//			stmt.setString(2, aluno.getEmail());
//			stmt.setString(3, aluno.getCpf());
//			stmt.setString(4, aluno.getMatricula());
//			stmt.setString(5, aluno.getTipomembro().name());
//			stmt.setInt(6, aluno.getDebitomultas());
//			stmt.setString(7, aluno.getStatusmembro().name());
//			stmt.setString(8, aluno.getCurso());
//			stmt.setInt(9, aluno.getId());
//			
//			stmt.executeUpdate();
//			
//		} catch (Exception e) {
//			throw new DbException(e.getMessage());
//		}finally {
//			ConnectionDb.closeStatement(stmt);
//			ConnectionDb.closeConnection(conn);
//		}
//	}
	
}
