package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.data.acervo.implement.EbookRepository;
import br.edu.ifpe.lpoo.project.data.acervo.implement.ExemplarRepository;
import br.edu.ifpe.lpoo.project.data.acervo.implement.LivroRepository;
import br.edu.ifpe.lpoo.project.data.acervo.implement.PeriodicoRepository;
import br.edu.ifpe.lpoo.project.data.empretimos.implement.EmprestimoRepository;
import br.edu.ifpe.lpoo.project.data.membros.implement.AlunoRepository;
import br.edu.ifpe.lpoo.project.entities.emprestimo.Emprestimo;
import br.edu.ifpe.lpoo.project.enums.StatusEmprestimo;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.ui.dto.AlunoDTO;
import br.edu.ifpe.lpoo.project.ui.dto.LivroDTO;

import java.time.LocalDate;

public class EmprestimoService {
    LivroRepository livroRepository;
    ExemplarRepository exemplarRepository;
    EbookRepository ebookRepository;
    PeriodicoRepository periodicoRepository;
    AlunoRepository alunoRepository;
    EmprestimoRepository emprestimoRepository;

    LocalDate localDate;
    public EmprestimoService() {

    }

    public void EmprestarLivro (LivroDTO livroDTO, AlunoDTO alunoDTO) {
        if (livroRepository.existItem(livroDTO.getId())) {
            throw new BusinessExcepition("id do livro não encontrado para empretimo");
        }
        if (alunoRepository.existMembro(alunoDTO.getCpf())) {
            throw new BusinessExcepition("cpf do menbro não encontrado para empretimo");
        }

        if (alunoDTO.getStatusmembro() != StatusMembro.ATIVO) {
            throw new BusinessExcepition("status do membro deve ser ativo");
        }

        LocalDate dataEmprestimo = LocalDate.now();

        LocalDate dataParaDevolucao = dataEmprestimo.plusDays(7);

        LocalDate dataDevolucao = null;

        StatusEmprestimo statusEmprestimo = StatusEmprestimo.ABERTO;

        Emprestimo emprestimo = new Emprestimo(livroDTO.getId(), alunoDTO.getId(), dataEmprestimo, dataParaDevolucao, dataDevolucao, statusEmprestimo);

        emprestimoRepository.insert(emprestimo);
    }
}
