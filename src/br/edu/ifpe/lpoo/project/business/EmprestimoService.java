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
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;

import java.time.LocalDate;

public class EmprestimoService {
    private final ExemplarRepository exemplarRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final PesquisadorRepository pesquisadorRepository;
    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService(
            ExemplarRepository exemplarRepository,
            EbookRepository ebookRepository,
            PeriodicoRepository periodicoRepository,
            AlunoRepository alunoRepository,
            ProfessorRepository professorRepository,
            PesquisadorRepository pesquisadorRepository,
            EmprestimoRepository emprestimoRepository) {
        this.exemplarRepository = exemplarRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.pesquisadorRepository = pesquisadorRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

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
            throw new BusinessExcepition("Exemplar com ID " + idExemplar + " não encontrado para empréstimo.");
        }

        if (exemplar.isDisponivel() != StatusExemplar.DISPONIVEL) {
            throw new BusinessExcepition("Exemplar com ID " + idExemplar + " não está disponível para empréstimo. Status: " + exemplar.isDisponivel());
        }

        Membro membro = switch (tipoMembro) {
            case ALUNO -> alunoRepository.buscarPorCPF(membroCpf);
            case PROFESSOR -> professorRepository.buscarPorCPF(membroCpf);
            case PESQUISADOR -> pesquisadorRepository.buscarPorCPF(membroCpf);
            default -> throw new IllegalArgumentException("Tipo de membro desconhecido.");
        };

        if (membro == null) {
            throw new BusinessExcepition("Membro com CPF " + membroCpf + " não encontrado para empréstimo.");
        }

        if (membro.getStatusmembro() != StatusMembro.ATIVO) {
            throw new BusinessExcepition("O status do membro deve ser ATIVO para realizar empréstimos.");
        }

        LocalDate dataEmprestimo = LocalDate.now();
        int prazoEmDias = getPrazoEmprestimo(membro);
        LocalDate dataParaDevolucao = dataEmprestimo.plusDays(prazoEmDias);
        StatusEmprestimo statusEmprestimo = StatusEmprestimo.ABERTO;

        Emprestimo emprestimo = new Emprestimo(exemplar.getIdExemplar(), membro.getId(), dataEmprestimo, dataParaDevolucao, null, statusEmprestimo);
        exemplar.setDisponivel(StatusExemplar.EMPRESTADO);
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
