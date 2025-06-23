package br.edu.ifpe.lpoo.project.data.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import br.edu.ifpe.lpoo.project.data.ConnectionDb;
import br.edu.ifpe.lpoo.project.data.IPeriodicoRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.ItemAcervo;
import br.edu.ifpe.lpoo.project.entities.acervo.Periodico;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

public class PeriodicoRepository implements IPeriodicoRepository{
	
	
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
		
		Periodico periodico = new Periodico(titulo, autor, anoPublicacao, editora, idioma, issn, numeroEdicao, volume, genero);
		periodico.setId(idPeriodico);
		
		return periodico;
	}
	
	@Override
	public void insert(ItemAcervo item) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		Periodico periodico = (Periodico) item; 
		
		String consulta = "INSERT INTO periodico (issn, titulo, autor, numero_edicao, volume, editora, idioma, ano_publicacao, genero) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setString(1, periodico.getIssn());
			stmt.setString(2,  periodico.getTitulo());
			stmt.setString(3, periodico.getAutor());
			stmt.setInt(4, periodico.getNumeroEdicao());
			stmt.setInt(5, periodico.getVolume());
			stmt.setString(6, periodico.getEditora());
			stmt.setString(7, periodico.getIdioma());
			stmt.setInt(8, periodico.getAnoPublicacao());
			stmt.setString(9, periodico.getGenero());
			
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
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		boolean exist = false;
		
		Periodico periodico = (Periodico) item; 
		
		String consulta = "SELECT * FROM periodico WHERE "
						+"issn = ? OR (titulo = ? AND numero_edicao = ? AND volume = ?)";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setString(1, periodico.getIssn());
			stmt.setString(2, periodico.getTitulo());
			stmt.setInt(3, periodico.getNumeroEdicao());
			stmt.setInt(4, periodico.getVolume());
			
			rst = stmt.executeQuery();
			
			exist = rst.next();
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		
		return exist;
	}
	
	@Override
	public Periodico buscarPorId(int idItem) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		Periodico periodico = null;
		
		String consulta = "SELECT * FROM periodico WHERE id_periodico = ?";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setInt(1, idItem);
			
			rst = stmt.executeQuery();
			
			if(rst.next()) {
				
				periodico = instanciarPeriodico(rst);
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		
		return periodico;
	}
	
	@Override
	public List<Periodico> buscarTodos(){
		
		List<Periodico> periodicos = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String consulta = "SELECT * FROM periodico";
		
		try {
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			rst = stmt.executeQuery();
			
			while(rst.next()) {
				
				periodicos.add(instanciarPeriodico(rst));
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		
		return periodicos;
	}
	
	@Override
	public List<Periodico> buscarPorTermo(String termo){
		
		List<Periodico> periodicos = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		String buscaTermo = "%" + termo.toLowerCase() + "%";
		
		String consulta = "SELECT * FROM periodico WHERE "
							+ "LOWER(issn) LIKE ?" 
							+ "OR LOWER(titulo) LIKE ? "
							+ "OR LOWER(autor) LIKE ?"
							+ "ORDER BY titulo";
		
		try {
			
			conn = ConnectionDb.getConnection();
			stmt = conn.prepareStatement(consulta);
			
			stmt.setString(1, buscaTermo);
			stmt.setString(2, buscaTermo);
			stmt.setString(3, buscaTermo);
			
			rst = stmt.executeQuery();
			
			while(rst.next()) {
				
				periodicos.add(instanciarPeriodico(rst));
			}
			
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDb.closeResultSet(rst);
			ConnectionDb.closeStatement(stmt);
			ConnectionDb.closeConnection(conn);
		}
		
		return periodicos;
	}
}
