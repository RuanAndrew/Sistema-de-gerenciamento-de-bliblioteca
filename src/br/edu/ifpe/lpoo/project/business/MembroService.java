package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.data.membros.implement.AlunoRepository;
import br.edu.ifpe.lpoo.project.data.membros.implement.PesquisadorRepository;
import br.edu.ifpe.lpoo.project.data.membros.implement.ProfessorRepository;
import br.edu.ifpe.lpoo.project.entities.membros.Aluno;
import br.edu.ifpe.lpoo.project.entities.membros.Membro;
import br.edu.ifpe.lpoo.project.entities.membros.Professor;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessException;
import br.edu.ifpe.lpoo.project.exceptions.DbException;
import br.edu.ifpe.lpoo.project.entities.membros.Pesquisador;


import java.util.ArrayList;
import java.util.List;

public class MembroService {
	private final AlunoRepository alunoRepository;
	private final ProfessorRepository professorRepository;
	private final PesquisadorRepository pesquisadorRepository;

	public MembroService() {
		this.alunoRepository = new AlunoRepository();
		this.professorRepository = new ProfessorRepository();
		this.pesquisadorRepository = new PesquisadorRepository();
	}

	public void cadastrarAluno(String nome, String cpf, String email, String matricula, String curso) {

		if (nome == null || nome.isBlank()) {
			throw new BusinessException("O nome é obrigatório");
		}
		if (cpf == null || cpf.isBlank()) {
			throw new BusinessException("O CPF é obrigatório");
		}
		if (email == null || email.isBlank()) {
			throw new BusinessException("O email é obrigatório");
		}
		if (matricula == null || matricula.isBlank()) {
			throw new BusinessException("A matricula é obrigatório");
		}
		if (curso == null || curso.isBlank()) {
			throw new BusinessException("O curso é obrigatório");
		}

		if (!cpf.matches("\\d{11}")) {
			throw new BusinessException("O CPF deve conter exatamente 11 números.");
		}
		if (email.length() > 255) {
			throw new BusinessException("O email não pode ter mais de 255 caracteres.");
		}
		if (matricula.length() > 10) {
			throw new BusinessException("A matrícula não pode ter mais de 10 caracteres.");
		}
		if (nome.length() > 150) {
			throw new BusinessException("O nome não pode ter mais de 150 caracteres.");
		}
		if (curso.length() > 100) {
			throw new BusinessException("O nome do curso não pode ter mais de 100 caracteres.");
		}

		Aluno aluno = new Aluno(nome, email, cpf, matricula, TipoMembro.ALUNO, 0, StatusMembro.ATIVO, curso);

		boolean exist = alunoRepository.existMembro(aluno);

		if (!exist) {
			try {
				alunoRepository.insert(aluno);
			} catch (DbException e) {
				throw new BusinessException("Erro ao cadastrar aluno: " + e.getMessage());
			}
		} else {
			throw new BusinessException("Esse aluno já está cadastrado no sistema.");
		}
	}

	public void cadastrarProfessor(String nome, String cpf, String email, String matricula, String areaAtuacao, String departamento) {
		if (nome == null || nome.isBlank()) {
			throw new BusinessException("O nome é obrigatório");
		}
		if (cpf == null || cpf.isBlank()) {
			throw new BusinessException("O CPF é obrigatório");
		}
		if (email == null || email.isBlank()) {
			throw new BusinessException("O email é obrigatório");
		}
		if (matricula == null || matricula.isBlank()) {
			throw new BusinessException("A matricula é obrigatória");
		}
		if (areaAtuacao == null || areaAtuacao.isBlank()) {
			throw new BusinessException("A area de atuação é obrigatória");
		}
		if (departamento == null || departamento.isBlank()) {
			throw new BusinessException("O departamento é obrigatório");
		}

		if (!cpf.matches("\\d{11}")) {
			throw new BusinessException("O CPF deve conter exatamente 11 números.");
		}
		if (email.length() > 255) {
			throw new BusinessException("O email não pode ter mais de 255 caracteres.");
		}
		if (matricula.length() > 10) {
			throw new BusinessException("A matrícula não pode ter mais de 10 caracteres.");
		}
		if (nome.length() > 150) {
			throw new BusinessException("O nome não pode ter mais de 150 caracteres.");
		}

		Professor professor = new Professor(nome, email, cpf, matricula, TipoMembro.PROFESSOR, 0, StatusMembro.ATIVO, areaAtuacao, departamento);

		boolean exist = professorRepository.existMembro(professor);

		if (!exist) {
			try {
				professorRepository.insert(professor);
			} catch (DbException e) {
				throw new BusinessException("Erro ao cadastrar professor: " + e.getMessage());
			}
		} else {
			throw new BusinessException("Esse professor já está cadastrado no sistema.");
		}
	}

	public void cadastrarPesquisador(String nome, String cpf, String email, String matricula, String instituicao) {
		if (nome == null || nome.isBlank()) {
			throw new BusinessException("O nome é obrigatório");
		}
		if (cpf == null || cpf.isBlank()) {
			throw new BusinessException("O CPF é obrigatório");
		}
		if (email == null || email.isBlank()) {
			throw new BusinessException("O email é obrigatório");
		}
		if (matricula == null || matricula.isBlank()) {
			throw new BusinessException("A matricula é obrigatória");
		}
		if (instituicao == null || instituicao.isBlank()) {
			throw new BusinessException("A instituição é obrigatória");
		}

		if (!cpf.matches("\\d{11}")) {
			throw new BusinessException("O CPF deve conter exatamente 11 números.");
		}
		if (email.length() > 255) {
			throw new BusinessException("O email não pode ter mais de 255 caracteres.");
		}
		if (matricula.length() > 10) {
			throw new BusinessException("A matrícula não pode ter mais de 10 caracteres.");
		}
		if (nome.length() > 150) {
			throw new BusinessException("O nome não pode ter mais de 150 caracteres.");
		}

		Pesquisador pesquisador = new Pesquisador(nome, email, cpf, matricula, TipoMembro.PESQUISADOR, 0, StatusMembro.ATIVO, instituicao);

		boolean exist = pesquisadorRepository.existMembro(pesquisador);

		if (!exist) {
			try {
				pesquisadorRepository.insert(pesquisador);
			} catch (DbException e) {
				throw new BusinessException("Erro ao cadastrar Pesquisador: " + e.getMessage());
			}
		} else {
			throw new BusinessException("Esse Pesquisador já está cadastrado no sistema.");
		}
	}

	public void atualizarMembro(Membro membro, StatusMembro novoStatus) {
		if (membro == null || membro.getCpf() == null || membro.getCpf().isBlank()) {
			throw new BusinessException("CPF obrigatório para atualizar membro.");
		}

		boolean existe = false;

		try {
			switch (membro.getTipomembro()) {
				case ALUNO -> {
					existe = alunoRepository.existMembro(membro.getCpf());
					if (existe) {
						alunoRepository.atualizar((Aluno) membro);
					}
				}
				case PROFESSOR -> {
					existe = professorRepository.existMembro(membro.getCpf());
					if (existe) {
						professorRepository.atualizar((Professor) membro);
					}
				}
				case PESQUISADOR -> {
					existe = pesquisadorRepository.existMembro(membro.getCpf());
					if (existe) {
						pesquisadorRepository.atualizar((Pesquisador) membro);
					}
				}
				default -> throw new BusinessException("membro não encontrado para a atualização");
			}
		}catch (NumberFormatException e) {
			throw new BusinessException("Formato de cpf inválido para deleção: " + membro.getCpf());
		}catch (DbException e) {
			throw new BusinessException("Erro de banco de dados ao deletar item de acervo: " + e.getMessage());
		}
	}
		
	 public Membro buscarmembroPorcpf (String cpfTexto) {
	        try {

	            Aluno aluno = alunoRepository.buscarPorCPF(cpfTexto);
	            if (aluno != null) {return aluno;}

	            Professor professor = professorRepository.buscarPorCPF(cpfTexto);
	            if (professor != null) {return professor;}

	            Pesquisador pesquisador = pesquisadorRepository.buscarPorCPF(cpfTexto);
	            if (pesquisador != null) {return pesquisador;}

	            return null;
	        }catch (NumberFormatException e) {
	            throw new BusinessException("Formato de cpf invalido: " + cpfTexto);
	        }catch (DbException e) {
	            throw new BusinessException("Erro ao buscar Membros por cpf: " + e.getMessage());
	        }
	    }

	 public List<Membro> listarTodosItens () {
		 List<Membro> todosItens = new ArrayList<>();
		 try {
			 for (Aluno aluno : alunoRepository.buscarTodos()) {
				 if (aluno.getStatusmembro() != StatusMembro.INATIVO) {
					 todosItens.add(aluno);
				 }
			 }
			 for (Professor professor : professorRepository.buscarTodos()) {
				 if (professor.getStatusmembro() != StatusMembro.INATIVO) {
					 todosItens.add(professor);
				 }
			 }
			 for (Pesquisador pesquisador : pesquisadorRepository.buscarTodos()) {
				 if (pesquisador.getStatusmembro() != StatusMembro.INATIVO) {
					 todosItens.add(pesquisador);
				 }
			 }
		 } catch (DbException e) {
			 throw new BusinessException("Erro ao listar todos os Membros: " + e.getMessage());
		 }
		 return todosItens;
	 }
	public void excluirMembro(String cpf, TipoMembro tipoMembro) {
		if (cpf == null) {
			throw new BusinessException("ID do item não pode ser nulo ou vazio para deleção.");
		}
		try {
			Membro membroParaDeletar = buscarmembroPorcpf(cpf);

			if (membroParaDeletar == null) {
				throw new BusinessException("Item de acervo com ID " + cpf + " não encontrado para deleção");
			}

			switch (tipoMembro) {
				case ALUNO ->
						alunoRepository.delete(membroParaDeletar.getId());
				case PESQUISADOR ->
						pesquisadorRepository.delete(membroParaDeletar.getId());
				case PROFESSOR ->
						professorRepository.delete(membroParaDeletar.getId());
				default ->
						throw new BusinessException("Tipo de membro desconhecido para deleção.");
			}
		} catch (NumberFormatException e) {
			throw new BusinessException("Formato de cpf inválido para deleção: " + cpf);
		} catch (DbException e) {
			throw new BusinessException("Erro de banco de dados ao deletar membro: " + e.getMessage());
		}
	}

}