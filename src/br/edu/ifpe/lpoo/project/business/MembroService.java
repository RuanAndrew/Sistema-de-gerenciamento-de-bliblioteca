package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.data.membros.implement.AlunoRepository;
import br.edu.ifpe.lpoo.project.data.membros.implement.PesquisadorRepository;
import br.edu.ifpe.lpoo.project.data.membros.implement.ProfessorRepository;
import br.edu.ifpe.lpoo.project.entities.membros.Aluno;
import br.edu.ifpe.lpoo.project.entities.membros.Membro;
import br.edu.ifpe.lpoo.project.entities.membros.Professor;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;
import br.edu.ifpe.lpoo.project.entities.membros.Pesquisador;


import java.util.ArrayList;
import java.util.List;

public class MembroService {
	AlunoRepository alunoRepository;
	ProfessorRepository professorRepository;
	PesquisadorRepository pesquisadorRepository;

	public void cadastrarAluno(String nome, String cpf, String email, String matricula, String curso) {

		if (nome == null || nome.isBlank()) {
			throw new BusinessExcepition("O nome é obrigatório");
		}
		if (cpf == null || cpf.isBlank()) {
			throw new BusinessExcepition("O CPF é obrigatório");
		}
		if (email == null || email.isBlank()) {
			throw new BusinessExcepition("O email é obrigatório");
		}
		if (matricula == null || matricula.isBlank()) {
			throw new BusinessExcepition("A matricula é obrigatório");
		}
		if (curso == null || curso.isBlank()) {
			throw new BusinessExcepition("O curso é obrigatório");
		}

		if (!cpf.matches("\\d{11}")) {
			throw new BusinessExcepition("O CPF deve conter exatamente 11 números.");
		}
		if (email.length() > 255) {
			throw new BusinessExcepition("O email não pode ter mais de 255 caracteres.");
		}
		if (matricula.length() > 10) {
			throw new BusinessExcepition("A matrícula não pode ter mais de 10 caracteres.");
		}
		if (nome.length() > 150) {
			throw new BusinessExcepition("O nome não pode ter mais de 150 caracteres.");
		}
		if (curso.length() > 100) {
			throw new BusinessExcepition("O nome do curso não pode ter mais de 100 caracteres.");
		}

		Aluno aluno = new Aluno(nome, email, cpf, matricula, TipoMembro.ALUNO, 0, StatusMembro.ATIVO, curso);

		alunoRepository = new AlunoRepository();

		boolean exist = alunoRepository.existMembro(aluno);

		if (!exist) {
			try {
				alunoRepository.insert(aluno);
			} catch (DbException e) {
				throw new BusinessExcepition("Erro ao cadastrar aluno: " + e.getMessage());
			}
		} else {
			throw new BusinessExcepition("Esse aluno já está cadastrado no sistema.");
		}
	}

	public void cadastrarProfessor(String nome, String cpf, String email, String matricula, String areaAtuacao, String departamento) {
		if (nome == null || nome.isBlank()) {
			throw new BusinessExcepition("O nome é obrigatório");
		}
		if (cpf == null || cpf.isBlank()) {
			throw new BusinessExcepition("O CPF é obrigatório");
		}
		if (email == null || email.isBlank()) {
			throw new BusinessExcepition("O email é obrigatório");
		}
		if (matricula == null || matricula.isBlank()) {
			throw new BusinessExcepition("A matricula é obrigatória");
		}
		if (areaAtuacao == null || areaAtuacao.isBlank()) {
			throw new BusinessExcepition("A area de atuação é obrigatória");
		}
		if (departamento == null || departamento.isBlank()) {
			throw new BusinessExcepition("O departamento é obrigatório");
		}

		if (!cpf.matches("\\d{11}")) {
			throw new BusinessExcepition("O CPF deve conter exatamente 11 números.");
		}

		Professor professor = new Professor(nome, email, cpf, matricula, TipoMembro.PROFESSOR, 0, StatusMembro.ATIVO, areaAtuacao, departamento);

		professorRepository = new ProfessorRepository();

		boolean exist = professorRepository.existMembro(professor);

		if (!exist) {
			try {
				professorRepository.insert(professor);
			} catch (DbException e) {
				throw new BusinessExcepition("Erro ao cadastrar professor: " + e.getMessage());
			}
		} else {
			throw new BusinessExcepition("Esse professor já está cadastrado no sistema.");
		}
	}

	public void CadastrarPesquisador(String nome, String cpf, String email, String matricula, String instituicao) {
		if (nome == null || nome.isBlank()) {
			throw new BusinessExcepition("O nome é obrigatório");
		}
		if (cpf == null || cpf.isBlank()) {
			throw new BusinessExcepition("O CPF é obrigatório");
		}
		if (email == null || email.isBlank()) {
			throw new BusinessExcepition("O email é obrigatório");
		}
		if (matricula == null || matricula.isBlank()) {
			throw new BusinessExcepition("A matricula é obrigatória");
		}
		if (instituicao == null || instituicao.isBlank()) {
			throw new BusinessExcepition("A instituição é obrigatória");
		}

		if (!cpf.matches("\\d{11}")) {
			throw new BusinessExcepition("O CPF deve conter exatamente 11 números.");
		}

		Pesquisador pesquisador = new Pesquisador(nome, email, cpf, matricula, TipoMembro.PESQUISADOR, 0, StatusMembro.ATIVO, instituicao);

		pesquisadorRepository = new PesquisadorRepository();

		boolean exist = pesquisadorRepository.existMembro(pesquisador);

		if (!exist) {
			try {
				pesquisadorRepository.insert(pesquisador);
			} catch (DbException e) {
				throw new BusinessExcepition("Erro ao cadastrar Pesquisador: " + e.getMessage());
			}
		} else {
			throw new BusinessExcepition("Esse Pesquisador já está cadastrado no sistema.");
		}
	}

	public void excluirMembro(Integer id) {
		if (id != null) {
			try {
				alunoRepository.delete(id);
				professorRepository.delete(id);
				pesquisadorRepository.delete(id);
			} catch (DbException e) {
				throw new BusinessExcepition("Erro ao deletar item de acervo: " + e.getMessage());
			}
		}
	}

	public void atualizarMembro(Aluno alunoAtualizado, Professor professorAtualizado, Pesquisador pesquisadoratualizado) {
		if (alunoAtualizado == null || alunoAtualizado.getCpf() == null || alunoAtualizado.getCpf().isBlank()) {
			throw new BusinessExcepition("CPF obrigatório para atualizar o aluno.");
		}

		alunoRepository = new AlunoRepository();

		boolean existe = alunoRepository.existMembro(alunoAtualizado);

		if (existe) {
			try {
				alunoRepository.atualizar(alunoAtualizado);
			} catch (DbException e) {
				throw new BusinessExcepition("Erro ao atualizar aluno: " + e.getMessage());
			}
		} else {
			throw new BusinessExcepition("Aluno não encontrado para atualização.");
		}
		if (professorAtualizado == null || professorAtualizado.getCpf() == null || professorAtualizado.getCpf().isBlank()) {
			throw new BusinessExcepition("CPF obrigatório para atualizar o professor.");
		}

		professorRepository = new ProfessorRepository();

		boolean existeprofessor = professorRepository.existMembro(professorAtualizado);

		if (existeprofessor) {
			try {
				professorRepository.atualizar(professorAtualizado);
			} catch (DbException e) {
				throw new BusinessExcepition("Erro ao atualizar professor: " + e.getMessage());
			}
		} else {
			throw new BusinessExcepition("Professor não encontrado para atualização.");
		}
		if (pesquisadoratualizado == null || pesquisadoratualizado.getCpf() == null || pesquisadoratualizado.getCpf().isBlank()) {
			throw new BusinessExcepition("CPF obrigatório para atualizar o professor.");
		}

		professorRepository = new ProfessorRepository();

		boolean existepesquisador = pesquisadorRepository.existMembro(pesquisadoratualizado);

		if (existepesquisador) {
			try {
				pesquisadorRepository.atualizar(pesquisadoratualizado);
			} catch (DbException e) {
				throw new BusinessExcepition("Erro ao atualizar pesquisador: " + e.getMessage());
			}
		} else {
			throw new BusinessExcepition("pesquisador não encontrado para atualização.");
		}
	}
		
	 public Membro buscarmembroPorId (int id) {
	        try {

	            Aluno aluno = alunoRepository.buscarPorId(id);
	            if (aluno != null) {return aluno;}

	            Professor professor = professorRepository.buscarPorId(id);
	            if (professor != null) {return professor;}

	            Pesquisador pesquisador = pesquisadorRepository.buscarPorId(id);
	            if (pesquisador != null) {return pesquisador;}

	            return null;
	        }catch (NumberFormatException e) {
	            throw new BusinessExcepition ("Formato de ID invalido: " + id);
	        }catch (DbException e) {
	            throw new BusinessExcepition("Erro ao buscar Membros por ID: " + e.getMessage());
	        }
	    }

	 public List<Membro> listarTodosItens () {
	        List<Membro> todosItens = new ArrayList<>();
	        try {
	            todosItens.addAll(alunoRepository.buscarTodos());
	            todosItens.addAll(professorRepository.buscarTodos());
	            todosItens.addAll(pesquisadorRepository.buscarTodos());
	        } catch (DbException e) {
	            throw new BusinessExcepition("Erro ao listar todos os Membros: " + e.getMessage());
	        }
	        return todosItens;
	    }

	    public List<Membro> buscarItensPorTermo (String termoBusca) {
	        List<Membro> resultados = new ArrayList<>();
	        try {
	            resultados.addAll(alunoRepository.buscarPorTermo(termoBusca));
	            resultados.addAll(professorRepository.buscarPorTermo(termoBusca));
	            resultados.addAll(pesquisadorRepository.buscarPorTermo(termoBusca));
	        } catch (DbException e) {
	            throw new BusinessExcepition("Erro ao buscar Membros por termo: " + e.getMessage());
	        }
	        return resultados;
	    }
}