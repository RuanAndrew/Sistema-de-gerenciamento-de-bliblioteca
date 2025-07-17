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
	AlunoRepository alunoRepository;
	ProfessorRepository professorRepository;
	PesquisadorRepository pesquisadorRepository;

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

		alunoRepository = new AlunoRepository();

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

		Professor professor = new Professor(nome, email, cpf, matricula, TipoMembro.PROFESSOR, 0, StatusMembro.ATIVO, areaAtuacao, departamento);

		professorRepository = new ProfessorRepository();

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

		Pesquisador pesquisador = new Pesquisador(nome, email, cpf, matricula, TipoMembro.PESQUISADOR, 0, StatusMembro.ATIVO, instituicao);

		pesquisadorRepository = new PesquisadorRepository();

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

	public void excluirMembro(Integer cpf) {
		if (cpf != null) {
			try {
				alunoRepository.delete(cpf);
				professorRepository.delete(cpf);
				pesquisadorRepository.delete(cpf);
			} catch (DbException e) {
				throw new BusinessException("Erro ao deletar item de acervo: " + e.getMessage());
			}
		}
	}

	public void atualizarMembro(Aluno alunoAtualizado, Professor professorAtualizado, Pesquisador pesquisadoratualizado) {
		if (alunoAtualizado == null || alunoAtualizado.getCpf() == null || alunoAtualizado.getCpf().isBlank()) {
			throw new BusinessException("CPF obrigatório para atualizar o aluno.");
		}

		alunoRepository = new AlunoRepository();

		boolean existe = alunoRepository.existMembro(alunoAtualizado);

		if (existe) {
			try {
				alunoRepository.atualizar(alunoAtualizado);
			} catch (DbException e) {
				throw new BusinessException("Erro ao atualizar aluno: " + e.getMessage());
			}
		} else {
			throw new BusinessException("Aluno não encontrado para atualização.");
		}
		if (professorAtualizado == null || professorAtualizado.getCpf() == null || professorAtualizado.getCpf().isBlank()) {
			throw new BusinessException("CPF obrigatório para atualizar o professor.");
		}

		professorRepository = new ProfessorRepository();

		boolean existeprofessor = professorRepository.existMembro(professorAtualizado);

		if (existeprofessor) {
			try {
				professorRepository.atualizar(professorAtualizado);
			} catch (DbException e) {
				throw new BusinessException("Erro ao atualizar professor: " + e.getMessage());
			}
		} else {
			throw new BusinessException("Professor não encontrado para atualização.");
		}
		if (pesquisadoratualizado == null || pesquisadoratualizado.getCpf() == null || pesquisadoratualizado.getCpf().isBlank()) {
			throw new BusinessException("CPF obrigatório para atualizar o professor.");
		}

		pesquisadorRepository = new PesquisadorRepository();

		boolean existepesquisador = pesquisadorRepository.existMembro(pesquisadoratualizado);

		if (existepesquisador) {
			try {
				pesquisadorRepository.atualizar(pesquisadoratualizado);
			} catch (DbException e) {
				throw new BusinessException("Erro ao atualizar pesquisador: " + e.getMessage());
			}
		} else {
			throw new BusinessException("pesquisador não encontrado para atualização.");
		}
	}
		
	 public Membro buscarmembroPorcpf (int cpf) {
	        try {

	            Aluno aluno = alunoRepository.buscarPorId(cpf);
	            if (aluno != null) {return aluno;}

	            Professor professor = professorRepository.buscarPorId(cpf);
	            if (professor != null) {return professor;}

	            Pesquisador pesquisador = pesquisadorRepository.buscarPorId(cpf);
	            if (pesquisador != null) {return pesquisador;}

	            return null;
	        }catch (NumberFormatException e) {
	            throw new BusinessException("Formato de cpf invalido: " + cpf);
	        }catch (DbException e) {
	            throw new BusinessException("Erro ao buscar Membros por cpf: " + e.getMessage());
	        }
	    }

	 public List<Membro> listarTodosItens () {
	        List<Membro> todosItens = new ArrayList<>();
	        try {
	            todosItens.addAll(alunoRepository.buscarTodos());
	            todosItens.addAll(professorRepository.buscarTodos());
	            todosItens.addAll(pesquisadorRepository.buscarTodos());
	        } catch (DbException e) {
	            throw new BusinessException("Erro ao listar todos os Membros: " + e.getMessage());
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
	            throw new BusinessException("Erro ao buscar Membros por termo: " + e.getMessage());
	        }
	        return resultados;
	    }
}