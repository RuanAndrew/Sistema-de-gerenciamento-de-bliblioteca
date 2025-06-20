package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.data.IExemplarRepository;
import br.edu.ifpe.lpoo.project.data.ILivroRepository;
import br.edu.ifpe.lpoo.project.data.implement.ExemplarRepository;
import br.edu.ifpe.lpoo.project.data.implement.LivroRepository;
import br.edu.ifpe.lpoo.project.entities.Exemplar;
import br.edu.ifpe.lpoo.project.entities.Livro;
import br.edu.ifpe.lpoo.project.enums.StatusExemplar;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AcervoService {
    Pattern pattern;
    Matcher matcher;
    public void CadastrarLivro (String titulo, String autor, String anoPublicacao, String editora, String isbn, String numeroPaginas, String genero, String idioma, String quantidadeExemplares) {
        if (titulo.isBlank()) {
            throw new BusinessExcepition("O campo titulo não pode ser vazio.");
        }
        if (autor.isBlank()) {
            throw new BusinessExcepition("O campo autor não pode ser vazio.");
        }
        if (anoPublicacao == null) {
            throw new BusinessExcepition("O campo ano de publicação não pode ser vazio.");
        }
        if (numeroPaginas == null) {
            throw new BusinessExcepition("O campo numero de paginas não pode ser vazio.");
        }
        if (genero.isBlank()) {
            throw new BusinessExcepition("O campo genero não pode ser vazio.");
        }
        if (idioma.isBlank()) {
            throw new BusinessExcepition("O campo idioma não pode ser vazio.");
        }
        if (Integer.parseInt(numeroPaginas) < 1) {
            throw new BusinessExcepition("O numero de paginas não pode ser menor que 1.");
        }
        if (Integer.parseInt(quantidadeExemplares) < 0) {
            throw new BusinessExcepition("O numero de exemplares não pode ser negativo.");
        }

        LocalDate currentDate = LocalDate.now();
        int anoAtual = currentDate.getYear();
        if (Integer.parseInt(anoPublicacao) > anoAtual) {
            throw new BusinessExcepition("O ano de publicação não pode ser maior que o ano atual.");
        }

        pattern = Pattern.compile("\\d{1,4}");
        matcher = pattern.matcher(anoPublicacao);
        if (!matcher.find()) {
            throw new BusinessExcepition("O ano deve ter de 1 a quatro digitos.");
        }

        Livro livro = new Livro(titulo,autor, Integer.parseInt(anoPublicacao), editora, idioma, isbn, Integer.parseInt(numeroPaginas), genero);
        ILivroRepository livroRepository = new LivroRepository();
        IExemplarRepository exemplarRepository = new ExemplarRepository();

        boolean exist = livroRepository.existItem(titulo);

        if(!exist) {
            try {
                livroRepository.insert(livro);
            }catch(DbException e) {
                throw new BusinessExcepition(e.getMessage());
            }

            for (int i = 1; i <= Integer.parseInt(quantidadeExemplares); i++) {

                try {
                    String registro = livro.getId() + "EXP" + i;
                    Exemplar exemplar = new Exemplar(livro.getId(), registro, StatusExemplar.DISPONIVEL);
                    exemplarRepository.insert(exemplar, livro.getId());
                }catch (DbException e) {
                    throw new BusinessExcepition(e.getMessage());
                }
            }
        }else {
            throw new BusinessExcepition("Esse livro ja esta cadastrado no sistema");
        }
    }
}
