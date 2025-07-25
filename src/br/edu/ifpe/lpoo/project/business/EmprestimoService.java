package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.data.acervo.implement.EbookRepository;
import br.edu.ifpe.lpoo.project.data.acervo.implement.ExemplarRepository;
import br.edu.ifpe.lpoo.project.data.acervo.implement.PeriodicoRepository;
import br.edu.ifpe.lpoo.project.data.empretimos.implement.EmprestimoRepository;
import br.edu.ifpe.lpoo.project.data.membros.implement.AlunoRepository;
import br.edu.ifpe.lpoo.project.data.membros.implement.PesquisadorRepository;
import br.edu.ifpe.lpoo.project.data.membros.implement.ProfessorRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Exemplar;
import br.edu.ifpe.lpoo.project.entities.emprestimo.Emprestimo;
import br.edu.ifpe.lpoo.project.entities.emprestimo.PrazoEmprestimo;
import br.edu.ifpe.lpoo.project.entities.membros.Aluno;
import br.edu.ifpe.lpoo.project.entities.membros.Membro;
import br.edu.ifpe.lpoo.project.entities.membros.Pesquisador;
import br.edu.ifpe.lpoo.project.entities.membros.Professor;
import br.edu.ifpe.lpoo.project.enums.*;
import br.edu.ifpe.lpoo.project.exceptions.BusinessException;

import java.time.LocalDate;

public class EmprestimoService {
    private final ExemplarRepository exemplarRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final PesquisadorRepository pesquisadorRepository;
    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService() {
        this.exemplarRepository = new ExemplarRepository();
        this.alunoRepository = new AlunoRepository();
        this.professorRepository = new ProfessorRepository();
        this.pesquisadorRepository = new PesquisadorRepository();
        this.emprestimoRepository = new EmprestimoRepository();
    }


    private void realizarEmprestimo(int idExemplar, String membroCpf, TipoMembro tipoMembro) {

        Exemplar exemplar = exemplarRepository.buscarPorId(idExemplar);
        if (exemplar == null) {
            throw new BusinessException("Exemplar com ID " + idExemplar + " não encontrado para empréstimo.");
        }

        if (exemplar.getStatus() != StatusExemplar.DISPONIVEL) {
            throw new BusinessException("Exemplar com ID " + idExemplar + " não está disponível para empréstimo. Status: " + exemplar.getStatus());
        }

        Membro membro = switch (tipoMembro) {
            case ALUNO -> alunoRepository.buscarPorCPF(membroCpf);
            case PROFESSOR -> professorRepository.buscarPorCPF(membroCpf);
            case PESQUISADOR -> pesquisadorRepository.buscarPorCPF(membroCpf);
            default -> throw new IllegalArgumentException("Tipo de membro desconhecido.");
        };

        if (membro == null) {
            throw new BusinessException("Membro com CPF " + membroCpf + " não encontrado para empréstimo.");
        }

        if (membro.getStatusmembro() != StatusMembro.ATIVO) {
            throw new BusinessException("O status do membro deve ser ATIVO para realizar empréstimos.");
        }

        LocalDate dataEmprestimo = LocalDate.now();
        int prazoEmDias = getPrazoEmprestimo(membro);
        LocalDate dataParaDevolucao = dataEmprestimo.plusDays(prazoEmDias);
        StatusEmprestimo statusEmprestimo = StatusEmprestimo.ABERTO;

        exemplar.setStatus(StatusExemplar.EMPRESTADO);

        Emprestimo emprestimo = new Emprestimo(exemplar.getIdExemplar(), membro.getId(), dataEmprestimo, dataParaDevolucao, null, statusEmprestimo);
        emprestimoRepository.insert(emprestimo);

    }

    private int getPrazoEmprestimo(Membro membro) {
        return switch (membro) {
            case Aluno aluno -> PrazoEmprestimo.PRAZO_ALUNO;
            case Professor professor -> PrazoEmprestimo.PRAZO_PROFESSOR;
            case Pesquisador pesquisador -> PrazoEmprestimo.PRAZO_PESQUISADOR;
            case null, default -> throw new IllegalArgumentException("Tipo de membro desconhecido. Não foi possível determinar o prazo de empréstimo.");
        };
    }

    public void emprestar(int idExemplar, String cpfMembro, TipoMembro tipoMembro) {
        realizarEmprestimo(idExemplar, cpfMembro, tipoMembro);
    }

}
