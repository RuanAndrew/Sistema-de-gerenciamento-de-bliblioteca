package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.data.acervo.implement.AlunoRepository;
import br.edu.ifpe.lpoo.project.data.acervo.implement.PesquisadorRepository;
import br.edu.ifpe.lpoo.project.data.acervo.implement.ProfessorRepository;
import br.edu.ifpe.lpoo.project.entities.membros.Aluno;
import br.edu.ifpe.lpoo.project.entities.membros.Professor;
import br.edu.ifpe.lpoo.project.enums.FormatoDigital;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;

import br.edu.ifpe.lpoo.project.exceptions.DbException;

import br.edu.ifpe.lpoo.project.entities.membros.Pesquisador;


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

	public void atualizarMembro(Aluno alunoAtualizado) {
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
	}
}