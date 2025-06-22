package br.edu.ifpe.lpoo.project.business;
import br.edu.ifpe.lpoo.project.data.IEbookRepository;
import br.edu.ifpe.lpoo.project.data.IExemplarRepository;
import br.edu.ifpe.lpoo.project.data.ILivroRepository;
import br.edu.ifpe.lpoo.project.data.implement.EbookRepository;
import br.edu.ifpe.lpoo.project.data.implement.ExemplarRepository;
import br.edu.ifpe.lpoo.project.data.implement.LivroRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.*;
import br.edu.ifpe.lpoo.project.enums.FormatoDigital;
import br.edu.ifpe.lpoo.project.enums.StatusExemplar;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AcervoService {
    LivroRepository livroRepository;
    ExemplarRepository exemplarRepository;
    EbookRepository ebookRepository;
    //PeriodicoRepository periodicoRepository;
    LocalDate currentDate = LocalDate.now();
    int anoAtual = currentDate.getYear();

    // Cadastrar itens ao acervo

    public void cadastrarLivro(String titulo, String autor, String anoPublicacao, String editora, String isbn, String numeroPaginas, String genero, String idioma, String quantidadeExemplares) {

        if (titulo.isBlank()) {
            throw new BusinessExcepition("O título é obrigatório.");
        }
        if (autor.isBlank()) {
            throw new BusinessExcepition("O autor é obrigatório.");
        }
        if (anoPublicacao.isBlank()) {
            throw new BusinessExcepition("O ano de publicação é obrigatório.");
        }
        if (numeroPaginas.isBlank()) {
            throw new BusinessExcepition("O número de páginas é obrigatório.");
        }
        if (genero.isBlank()) {
            throw new BusinessExcepition("O gênero é obrigatório.");
        }
        if (idioma.isBlank()) {
            throw new BusinessExcepition("O idioma é obrigatório.");
        }
        if (Integer.parseInt(numeroPaginas) < 1) {
            throw new BusinessExcepition("O número de páginas deve ser maior que zero.");
        }
        if (Integer.parseInt(quantidadeExemplares) < 0) {
            throw new BusinessExcepition("A quantidade de exemplares não pode ser negativa.");
        }
        if (Integer.parseInt(anoPublicacao) > anoAtual) {
            throw new BusinessExcepition("O ano de publicação não pode ser futuro.");
        }

        Pattern anoPublicacaoPattern = Pattern.compile("^\\d{1,4}$");
        Matcher anoPublicacaoMatcher = anoPublicacaoPattern.matcher(anoPublicacao);
        if (!anoPublicacaoMatcher.matches()) {
            throw new BusinessExcepition("O ano deve ter de 1 a quatro digitos.");
        }

        ItemAcervo livro = new Livro(titulo,autor, Integer.parseInt(anoPublicacao), editora, idioma, isbn, Integer.parseInt(numeroPaginas), genero);
        livroRepository = new LivroRepository();
        exemplarRepository = new ExemplarRepository();

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

    public void cadastrarEbook (String titulo,String autor, String anoPublicacao, String editora, String isbn, String numeroPaginas, String genero, String idioma, String formatoDigital, String url) {
        if (titulo.isBlank()) {
            throw new BusinessExcepition("O título é obrigatório.");
        }
        if (autor.isBlank()) {
            throw new BusinessExcepition("O autor é obrigatório.");
        }
        if (anoPublicacao.isBlank()) {
            throw new BusinessExcepition("O ano de publicação é obrigatório.");
        }
        if (numeroPaginas.isBlank()) {
            throw new BusinessExcepition("O número de páginas é obrigatório.");
        }
        if (genero.isBlank()) {
            throw new BusinessExcepition("O gênero é obrigatório.");
        }
        if (idioma.isBlank()) {
            throw new BusinessExcepition("O idioma é obrigatório.");
        }
        if (formatoDigital.isBlank()) {
            throw new BusinessExcepition("O formato digital é obrigatório.");
        }
        if (url.isBlank()) {
            throw new BusinessExcepition("A URL e obrigatória.");
        }

        if (Integer.parseInt(numeroPaginas) < 1) {
            throw new BusinessExcepition("O número de páginas deve ser maior que zero.");
        }

        if (Integer.parseInt(anoPublicacao) > anoAtual) {
            throw new BusinessExcepition("O ano de publicação não pode ser futuro.");
        }

        Pattern anoPublicacaoPattern = Pattern.compile("\\d{1,4}");
        Matcher anoPublicacaoMatcher = anoPublicacaoPattern.matcher(anoPublicacao);
        if (!anoPublicacaoMatcher.matches()) {
            throw new BusinessExcepition("O ano deve ter de 1 a quatro digitos.");
        }

        FormatoDigital formatoDigitalNovo = null;
        for(FormatoDigital c : FormatoDigital.values()) {
            if (formatoDigital.equals(String.valueOf(c))) {
                formatoDigitalNovo = c;
                break;
            }
        }
        if(formatoDigitalNovo == null) {
            throw new BusinessExcepition("Formato digital invalido.");
        }

        ItemAcervo ebook = new Ebook(titulo,autor,Integer.parseInt(anoPublicacao),editora,idioma,isbn,Integer.parseInt(numeroPaginas),genero, formatoDigitalNovo,url);
        ebookRepository = new EbookRepository();

        boolean exist = ebookRepository.exist(ebook);

        if(!exist) {
            try {
            	ebookRepository.insert(ebook);
            }catch(DbException e) {
                throw new BusinessExcepition(e.getMessage());
            }

        }else {
            throw new BusinessExcepition("Esse ebook ja esta cadastrado no sistema");
        }
    }

    public void cadastrarPeriodico (String titulo,String autor, String anoPublicacao, String issn, String editora, String numeroEdicao, String volume, String genero, String idioma) {
        if (titulo.isBlank()) {
            throw new BusinessExcepition("O titulo e obrigatório");
        }
        if (autor.isBlank()) {
            throw  new BusinessExcepition("O autor e obrigatório");
        }
        if (numeroEdicao.isBlank()) {
            throw new BusinessExcepition("O numero da edição e obrigatório");
        }
        if (volume.isBlank()) {
            throw new BusinessExcepition("O volume e obrigatório");
        }
        if (anoPublicacao.isBlank()) {
            throw new BusinessExcepition("O ano de publicação e obrigatório");
        }
        if (genero.isBlank()) {
            throw new BusinessExcepition("O genero e obrigatório");
        }
        if (idioma.isBlank()) {
            throw new BusinessExcepition("O idioma e obrigatório");
        }

        Pattern issnPattern = Pattern.compile("^\\d{4}[ -]?\\d{3}[\\dxORX]$");
        Matcher issnMatcher = issnPattern.matcher(issn);
        if (!issnMatcher.matches()) {
            throw new BusinessExcepition("ISSN invalido");
        }

        Pattern numeroEdicaoPattern = Pattern.compile("^\\d+$");
        Matcher numeroEdicaoMatcher = numeroEdicaoPattern.matcher(numeroEdicao);
        if (!numeroEdicaoMatcher.matches()) {
            throw new BusinessExcepition("O numero da edição deve conter somente numeros");
        }

        Pattern volumePattern = Pattern.compile("^\\d+$");
        Matcher volumeMatcher = volumePattern.matcher(volume);
        if (!volumeMatcher.matches()) {
            throw new BusinessExcepition("O volume deve conter somente numeros");
        }

        if (Integer.parseInt(anoPublicacao) > anoAtual) {
            throw new BusinessExcepition("O ano de publicação não pode ser futuro.");
        }

        Pattern anoPublicacaoPattern = Pattern.compile("\\d{1,4}");
        Matcher anoPublicacaoMatcher = anoPublicacaoPattern.matcher(anoPublicacao);
        if (!anoPublicacaoMatcher.matches()) {
            throw new BusinessExcepition("O ano deve ter de 1 a quatro digitos.");
        }
        /*

        ItemAcervo periodico = new Periodico (titulo, autor, Integer.parseInt(anoPublicacao), editora, idioma, issn, Integer.parseInt(numeroEdicao), Integer.parseInt(volume), genero);
        periodicoRepository = new PeriodicoRepository();

        boolean exist = periodicoRepository.exist(periodico);

        if(!exist) {
            try {
                periodicoRepository.insert(periodico);
            }catch(DbException e) {
                throw new BusinessExcepition(e.getMessage());
            }

        }else {
            throw new BusinessExcepition("Esse periodico ja esta cadastrado no sistema");
        }
        */
    }

    public void cadastrarPorCodigo () {}

    // Atualizar itens do acervo

    public void atualizarItem () {}

    // Deletar itens do acervo

    public void deletarItem () {}

    // Vizualizar itens do acervo

    /*
    public ItemAcervo buscarItemPorId (int id) {
        try {
            ItemAcervo item = livroRepository.buscarPorId(id);
            if (item != null) {return item;}

            item = ebookRepository.buscarPorId(id);
            if (item != null) {return item;}

            item = periodicoRepository.buscarPorId(id);
            if (item != null) return item;

            return null;
        } catch (SQLException e) {
            throw new BusinessExcepition("Erro ao buscar item de acervo por ID: " + e.getMessage());
        }
    }

    public List<ItemAcervo> listarTodosItens () {
        List<ItemAcervo> todosItens = new ArrayList<>();
        try {
            todosItens.addAll(livroRepository.buscarTodos());
            todosItens.addAll(ebookRepository.buscarTodos());
            todosItens.addAll(periodicoRepository.buscarTodos());
        } catch (SQLException e) {
            throw new BusinessExcepition("Erro ao listar todos os itens do acervo: " + e.getMessage());
        }
        return todosItens;
    }

    public List<ItemAcervo> buscarItensPorTermo (String termoBusca) {
        List<ItemAcervo> resultados = new ArrayList<>();
        try {
            resultados.addAll(livroRepository.buscarPorTermo(termoBusca));
            resultados.addAll(ebookRepository.buscarPorTermo(termoBusca));
            resultados.addAll(periodicoRepository.buscarPorTermo(termoBusca));
        } catch (SQLException e) {
            throw new BusinessExcepition("Erro ao buscar itens de acervo por termo: " + e.getMessage(), e);
        }
        return resultados;
    }

     */


    // Gerenciar exemplares de itens do acervo

    public void adicionarExemplares () {}
    public void deletarExemplares () {}
    public void atualizarStatusExemplar () {}
    public List<Exemplar> listarExemplaresPorLivro () {
        return null;
    }
}

