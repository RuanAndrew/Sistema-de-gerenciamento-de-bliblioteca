package br.edu.ifpe.lpoo.project.data.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.IProfessorRepository;
import br.edu.ifpe.lpoo.project.entities.membros.Professor;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class ProfessorRepository implements IProfessorRepository{
	
private Professor instanciarProfessor(ResultSet rst) throws SQLException{
		
		int idProfessor = rst.getInt("id_professor");
		String nome = rst.getString("nome");
		String email = rst.getString("email");
		String cpf = rst.getString("cpf");
		String matricula = rst.getString("matricula");
		String tipo = rst.getString("tipo_membro").toUpperCase();
		TipoMembro tipoMembro = TipoMembro.valueOf(tipo);
		int debitoMultas = rst.getInt("debito_multas");
		String status = rst.getString("status_membro");
		StatusMembro statusMembro = StatusMembro.valueOf(status);
		String areaAtuacao = rst.getString("area_atuacao");
		String departamento = rst.getString("departamento");
		
		Professor professor = new Professor(nome, email, cpf, matricula, tipoMembro, debitoMultas, statusMembro, areaAtuacao, departamento);
		professor.setId(idProfessor);
		
		return professor;
		
	}

	@Override
	public void insert(Professor professor) {
		
		if(professor == null) {
			throw new DbException("Objeto tipo Professor não pode ser null");
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String consulta = "INSERT INTO professor (nome, email, cpf, matricula, tipo_membro, debito_multas, status_membro, area_atuacao, departamento) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, professor.getNome());
			stmt.setString(2, professor.getEmail());
			stmt.setString(3, professor.getCpf());
			stmt.setString(4, professor.getMatricula());
			stmt.setString(5, professor.getTipomembro().name());
			stmt.setInt(6, professor.getDebitomultas());
			stmt.setString(7, professor.getStatusmembro().name());
			stmt.setString(8, professor.getAreaAtuacao());
			stmt.setString(9, professor.getDepartamento());
			
			stmt.executeUpdate();
			
			rst = stmt.getGeneratedKeys();
			
			if(rst.next()) {
				professor.setId(rst.getInt(1));
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
	public boolean existMembro(Professor professor) {
		
		if(professor == null) {
			throw new DbException("Objeto tipo Professor não pode ser null");
		}
		
		
		boolean exists = false;
		
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String consulta = "SELECT * FROM professor WHERE cpf = ?";
		
		try {
			
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setString(1, professor.getCpf());
			
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
		
		String consulta = "DELETE FROM professor WHERE id_professor = ?";
		
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
	public Professor buscarPorId(int idMembro) {

		if(idMembro <= 0) {
			throw new DbException("Id inválido");
		}
		
		Professor professor = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String consulta = "SELECT * FROM professor WHERE id_professor = ?";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setInt(1, idMembro);
			
			rst = stmt.executeQuery();
			
			if(rst.next()) {
				professor = instanciarProfessor(rst);
			}
			
			
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
			
		return professor;
	}

	@Override
	public List<Professor> buscarTodos() {
		
		List<Professor> professores = new ArrayList<Professor>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String consulta = "SELECT * FROM professor";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			rst = stmt.executeQuery();
			
			while(rst.next()) {
				professores.add(instanciarProfessor(rst));
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
			
		return professores;
	}

	@Override
	public List<Professor> buscarPorTermo(String termo) {
		
		if(termo == null) {
			throw new DbException("O termo de pesquisa não pode ser null");
		}
		
		List<Professor> professores = new ArrayList<Professor>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String termoStmt = "%" + termo.toLowerCase() + "%";
		
		String consulta = "SELECT * FROM professor "
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
				professores.add(instanciarProfessor(rst));
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		return professores;
	}

	@Override
	public void atualizar(Professor professor) {
		
		if(professor == null) {
			throw new DbException("Professor não pode ser null");
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String consulta = "UPDATE professor "
						+ "SET nome = ?, email = ?, cpf = ?, matricula = ?, tipo_membro = ?, debito_multas = ?, status_membro = ?, area_atuacao = ?, departamento = ? "
						+ "WHERE id_professor = ?";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setString(1, professor.getNome());
			stmt.setString(2, professor.getEmail());
			stmt.setString(3, professor.getCpf());
			stmt.setString(4, professor.getMatricula());
			stmt.setString(5, professor.getTipomembro().name());
			stmt.setInt(6, professor.getDebitomultas());
			stmt.setString(7, professor.getStatusmembro().name());
			stmt.setString(8, professor.getAreaAtuacao());
			stmt.setString(9, professor.getDepartamento());
			stmt.setInt(10, professor.getId());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		
	}
}
