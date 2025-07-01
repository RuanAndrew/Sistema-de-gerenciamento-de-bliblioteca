package br.edu.ifpe.lpoo.project.business;
import br.edu.ifpe.lpoo.project.data.IPeriodicoRepository;
import br.edu.ifpe.lpoo.project.data.implement.EbookRepository;
import br.edu.ifpe.lpoo.project.data.implement.ExemplarRepository;
import br.edu.ifpe.lpoo.project.data.implement.LivroRepository;
import br.edu.ifpe.lpoo.project.data.implement.PeriodicoRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.*;
import br.edu.ifpe.lpoo.project.enums.FormatoDigital;
import br.edu.ifpe.lpoo.project.enums.StatusExemplar;
import br.edu.ifpe.lpoo.project.enums.TipoItemAcervo;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;
import br.edu.ifpe.lpoo.project.ui.dto.EbookDTO;
import br.edu.ifpe.lpoo.project.ui.dto.LivroDTO;
import br.edu.ifpe.lpoo.project.ui.dto.PeriodicoDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AcervoService {
    LivroRepository livroRepository;
    ExemplarRepository exemplarRepository;
    EbookRepository ebookRepository;
    PeriodicoRepository periodicoRepository;
    CatalogacaoService catalogacaoService;
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

        int parsedNumeroPaginas;
        try {
            parsedNumeroPaginas = Integer.parseInt(numeroPaginas);
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Número de páginas inválido: deve ser um valor numérico.");
        }
        int parsedQuantidadeExemplares;
        try {
            parsedQuantidadeExemplares = Integer.parseInt(quantidadeExemplares);
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Quantidade de exemplares inválida: deve ser um valor numérico.");
        }
        int parsedAnoPublicacao;
        try {
            parsedAnoPublicacao = Integer.parseInt(anoPublicacao);
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Ano de publicação inválido: deve ser um valor numérico.");
        }

        if (parsedNumeroPaginas < 1) {
            throw new BusinessExcepition("O número de páginas deve ser maior que zero.");
        }
        if (parsedQuantidadeExemplares < 0) {
            throw new BusinessExcepition("A quantidade de exemplares não pode ser negativa.");
        }
        if (parsedAnoPublicacao > anoAtual) {
            throw new BusinessExcepition("O ano de publicação não pode ser futuro.");
        }

        Pattern anoPublicacaoPattern = Pattern.compile("^\\d{1,4}$");
        Matcher anoPublicacaoMatcher = anoPublicacaoPattern.matcher(anoPublicacao);
        if (!anoPublicacaoMatcher.matches()) {
            throw new BusinessExcepition("O ano deve ter de 1 a quatro digitos.");
        }

        Livro livro = new Livro(titulo,autor, parsedAnoPublicacao, editora, idioma, isbn, parsedNumeroPaginas, genero);
        livroRepository = new LivroRepository();
        exemplarRepository = new ExemplarRepository();

        boolean exist = livroRepository.existItem(livro);

        if(!exist) {
            try {
                livroRepository.insert(livro);
            }catch(DbException e) {
                throw new BusinessExcepition(e.getMessage());
            }

            for (int i = 1; i <= parsedQuantidadeExemplares; i++) {

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

        int parsedNumeroPaginas;
        try {
            parsedNumeroPaginas = Integer.parseInt(numeroPaginas);
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Número de páginas inválido: deve ser um valor numérico.");
        }
        int parsedAnoPublicacao;
        try {
            parsedAnoPublicacao = Integer.parseInt(anoPublicacao);
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Ano de publicação inválido: deve ser um valor numérico.");
        }

        if (parsedNumeroPaginas < 0) {
            throw new BusinessExcepition("O número de páginas não pode ser menor que zero.");
        }

        if (parsedAnoPublicacao > anoAtual) {
            throw new BusinessExcepition("O ano de publicação não pode ser futuro.");
        }

        Pattern anoPublicacaoPattern = Pattern.compile("-?\\d{1,4}");
        Matcher anoPublicacaoMatcher = anoPublicacaoPattern.matcher(anoPublicacao);
        if (!anoPublicacaoMatcher.matches()) {
            throw new BusinessExcepition("O ano deve ter de 1 a quatro digitos.");
        }

        FormatoDigital formatoDigitalNovo = null;
        for(FormatoDigital c : FormatoDigital.values()) {
            if (formatoDigital.toUpperCase().equals(String.valueOf(c))) {
                formatoDigitalNovo = c;
                break;
            }
        }
        if(formatoDigitalNovo == null) {
            throw new BusinessExcepition("Formato digital invalido.");
        }

        Ebook ebook = new Ebook(titulo,autor,parsedAnoPublicacao,editora,idioma,isbn,parsedNumeroPaginas,genero, formatoDigitalNovo,url);
        ebookRepository = new EbookRepository();

        boolean exist = ebookRepository.existItem(ebook);

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

        int parsedAnoPublicacao;
        try {
            parsedAnoPublicacao = Integer.parseInt(anoPublicacao);
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Ano de publicação inválido: deve ser um valor numérico.");
        }
        int parsedNumeroEdicao;
        try {
            parsedNumeroEdicao = Integer.parseInt(numeroEdicao);
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Numero de edição inválido: deve ser um valor numérico.");
        }
        int parsedVolume;
        try {
            parsedVolume = Integer.parseInt(volume);
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Volume inválido: deve ser um valor numérico.");
        }

        if (parsedAnoPublicacao > anoAtual) {
            throw new BusinessExcepition("O ano de publicação não pode ser futuro.");
        }
        if (parsedNumeroEdicao < 0) {
            throw new BusinessExcepition("O numero da edição não pode ser menor que zero");
        }
        if (parsedVolume < 0) {
            throw new BusinessExcepition("O volume não pode ser menor que zero");
        }


        Pattern issnPattern = Pattern.compile("^\\d{4}[ -]?\\d{3}[\\dxX]$");
        Matcher issnMatcher = issnPattern.matcher(issn);
        if (!issnMatcher.matches()) {
            throw new BusinessExcepition("ISSN invalido");
        }

        Pattern anoPublicacaoPattern = Pattern.compile("-?\\d{1,4}");
        Matcher anoPublicacaoMatcher = anoPublicacaoPattern.matcher(anoPublicacao);
        if (!anoPublicacaoMatcher.matches()) {
            throw new BusinessExcepition("O ano deve ter de um a quatro digitos.");
        }
      

        Periodico periodico = new Periodico (titulo, autor, parsedAnoPublicacao, editora, idioma, issn, parsedNumeroEdicao, parsedVolume, genero);
        IPeriodicoRepository periodicoRepository = new PeriodicoRepository();

        boolean exist = periodicoRepository.existItem(periodico);

        if(!exist) {
            try {
                periodicoRepository.insert(periodico);
            }catch(DbException e) {
                throw new BusinessExcepition(e.getMessage());
            }

        }else {
            throw new BusinessExcepition("Esse periodico ja esta cadastrado no sistema");
        }
        
    }

    public Livro buscarDadosParaPreenchimentoAutomatico(String isbn) {
        return catalogacaoService.buscarDadosLivroPorIsbn(isbn);
    }

    // Atualizar itens do acervo

    public void atualizarLivro (LivroDTO dto) {
        if (dto == null) {
            throw new BusinessExcepition("Dados de atualização de livro inválidos: ID não fornecido.");
        }

        int numeroPaginas;
        try {
            numeroPaginas = Integer.parseInt(dto.getNumeroPaginas());
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Número de páginas inválido: deve ser um valor numérico.");
        }
        int anoPublicacao;
        try {
            anoPublicacao = Integer.parseInt(dto.getAnoPublicacao());
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Ano de publicação inválido: deve ser um valor numérico.");
        }

        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new BusinessExcepition("O título do livro não pode ser vazio na atualização.");
        }
        if (dto.getAutor() == null || dto.getAutor().isBlank()) {
            throw new BusinessExcepition("O autor do livro não pode ser vazio na atualização.");
        }
        if (dto.getAnoPublicacao() == null || dto.getAnoPublicacao().isBlank()) {
            throw new BusinessExcepition("O ano de publicação do livro não pode ser vazio na atualização.");
        }
        if (dto.getNumeroPaginas() == null || dto.getNumeroPaginas().isBlank()) {
            throw new BusinessExcepition("O numero de paginas do livro não pode ser vazio na atualização.");
        }
        if (dto.getGenero() == null || dto.getGenero().isBlank()) {
            throw new BusinessExcepition("O genero do livro não pode ser vazio na atualização.");
        }
        if (dto.getIdioma() == null || dto.getIdioma().isBlank()) {
            throw new BusinessExcepition("O idioma do livro não pode ser vazio na atualização.");
        }


        try {
            Livro livroAtualNoDB = livroRepository.buscarPorId(dto.getId());

            if (livroAtualNoDB == null) {
                throw new BusinessExcepition("Livro com ID " + dto.getId() + " não encontrado para atualização.");
            }

            livroAtualNoDB.setTitulo(dto.getTitulo());
            livroAtualNoDB.setAutor(dto.getAutor());
            livroAtualNoDB.setAnoPublicacao(anoPublicacao);
            livroAtualNoDB.setEditora(dto.getEditora());
            livroAtualNoDB.setIsbn(dto.getIsbn());
            livroAtualNoDB.setNumeroPaginas(numeroPaginas);
            livroAtualNoDB.setGenero(dto.getGenero());
            livroAtualNoDB.setIdioma(dto.getIdioma());

            livroRepository.atualizar(livroAtualNoDB);

        } catch (DbException e) {
            throw new BusinessExcepition("Erro de banco de dados ao atualizar livro: " + e.getMessage());
        }
    }

    public void atualizarEbook (EbookDTO dto) {
        if (dto == null) {
            throw new BusinessExcepition("Dados de atualização do ebook inválidos: ID não fornecido.");
        }

        int numeroPaginas;
        try {
            numeroPaginas = Integer.parseInt(dto.getNumeroPaginas());
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Número de páginas inválido: deve ser um valor numérico.");
        }
        int anoPublicacao;
        try {
            anoPublicacao = Integer.parseInt(dto.getAnoPublicacao());
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Ano de publicação inválido: deve ser um valor numérico.");
        }

        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new BusinessExcepition("O título do ebook não pode ser vazio na atualização.");
        }
        if (dto.getAutor() == null || dto.getAutor().isBlank()) {
            throw new BusinessExcepition("O autor do ebook não pode ser vazio na atualização.");
        }
        if (dto.getAnoPublicacao() == null || dto.getAnoPublicacao().isBlank()) {
            throw new BusinessExcepition("O ano de publicação do ebook não pode ser vazio na atualização.");
        }
        if (dto.getNumeroPaginas() == null || dto.getNumeroPaginas().isBlank()) {
            throw new BusinessExcepition("O numero de paginas do ebook não pode ser vazio na atualização.");
        }
        if (dto.getGenero() == null || dto.getGenero().isBlank()) {
            throw new BusinessExcepition("O genero do ebook não pode ser vazio na atualização.");
        }
        if (dto.getIdioma() == null || dto.getIdioma().isBlank()) {
            throw new BusinessExcepition("O idioma do ebook não pode ser vazio na atualização.");
        }
        if (dto.getUrl() == null || dto.getUrl().isBlank()) {
            throw new BusinessExcepition("A url do ebook não pode ser vazio na atualização.");
        }
        if (dto.getFormatoDigital() == null || dto.getFormatoDigital().isBlank()) {
            throw new BusinessExcepition("O formato digital do ebook não pode ser vazio na atualização.");
        }

        FormatoDigital formatoDigitalNovo = null;
        for(FormatoDigital c : FormatoDigital.values()) {
            if (dto.getFormatoDigital().equals(String.valueOf(c))) {
                formatoDigitalNovo = c;
                break;
            }
        }


        try {
            Ebook ebookAtualNoDB = ebookRepository.buscarPorId(dto.getId());

            if (ebookAtualNoDB == null) {
                throw new BusinessExcepition("Ebook com ID " + dto.getId() + " não encontrado para atualização.");
            }

            ebookAtualNoDB.setTitulo(dto.getTitulo());
            ebookAtualNoDB.setAutor(dto.getAutor());
            ebookAtualNoDB.setAnoPublicacao(anoPublicacao);
            ebookAtualNoDB.setEditora(dto.getEditora());
            ebookAtualNoDB.setIsbn(dto.getIsbn());
            ebookAtualNoDB.setNumeroPaginas(numeroPaginas);
            ebookAtualNoDB.setGenero(dto.getGenero());
            ebookAtualNoDB.setIdioma(dto.getIdioma());
            ebookAtualNoDB.setUrl(dto.getUrl());
            ebookAtualNoDB.setFormatoDigital(formatoDigitalNovo);

            ebookRepository.atualizar(ebookAtualNoDB);

        } catch (DbException e) {
            throw new BusinessExcepition("Erro de banco de dados ao atualizar ebook: " + e.getMessage());
        }
    }

    public void atualizarPeriodico (PeriodicoDTO dto) {
        if (dto == null) {
            throw new BusinessExcepition("Dados de atualização do periodico inválidos: ID não fornecido.");
        }

        int numeroEdicao;
        try {
            numeroEdicao = Integer.parseInt(dto.getNumeroEdicao());
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Número da edição inválido: deve ser um valor numérico.");
        }
        int anoPublicacao;
        try {
            anoPublicacao = Integer.parseInt(dto.getAnoPublicacao());
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Ano de publicação inválido: deve ser um valor numérico.");
        }

        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new BusinessExcepition("O título do periodico não pode ser vazio na atualização.");
        }
        if (dto.getAutor() == null || dto.getAutor().isBlank()) {
            throw new BusinessExcepition("O autor do periodico não pode ser vazio na atualização.");
        }
        if (dto.getAnoPublicacao() == null || dto.getAnoPublicacao().isBlank()) {
            throw new BusinessExcepition("O ano de publicação do periodico não pode ser vazio na atualização.");
        }
        if (dto.getNumeroEdicao() == null || dto.getNumeroEdicao().isBlank()) {
            throw new BusinessExcepition("O numero da edição do periodico não pode ser vazio na atualização.");
        }
        if (dto.getGenero() == null || dto.getGenero().isBlank()) {
            throw new BusinessExcepition("O genero do periodico não pode ser vazio na atualização.");
        }
        if (dto.getIdioma() == null || dto.getIdioma().isBlank()) {
            throw new BusinessExcepition("O idioma do periodico não pode ser vazio na atualização.");
        }
        if (dto.getVolume() == null || dto.getVolume().isBlank()) {
            throw new BusinessExcepition("O volume do periodico não pode ser vazio na atualização.");
        }


        try {
            Periodico periodicoAtualNoDB = periodicoRepository.buscarPorId(dto.getId());

            if (periodicoAtualNoDB == null) {
                throw new BusinessExcepition("periodico com ID " + dto.getId() + " não encontrado para atualização.");
            }

            periodicoAtualNoDB.setTitulo(dto.getTitulo());
            periodicoAtualNoDB.setAutor(dto.getAutor());
            periodicoAtualNoDB.setAnoPublicacao(anoPublicacao);
            periodicoAtualNoDB.setEditora(dto.getEditora());
            periodicoAtualNoDB.setIssn(dto.getIssn());
            periodicoAtualNoDB.setNumeroEdicao(numeroEdicao);
            periodicoAtualNoDB.setGenero(dto.getGenero());
            periodicoAtualNoDB.setIdioma(dto.getIdioma());

            periodicoRepository.atualizar(periodicoAtualNoDB);

        } catch (DbException e) {
            throw new BusinessExcepition("Erro de banco de dados ao atualizar periodico: " + e.getMessage());
        }
    }

    // Deletar itens do acervo

    public void deletarItem (Integer id, TipoItemAcervo tipoItemAcervo) {
        if (id == null) {
            throw new BusinessExcepition("ID do item não pode ser nulo ou vazio para deleção.");
        }
        try {
            ItemAcervo itemParaDeletar = buscarItemPorId(id);

            if (itemParaDeletar == null) {
                throw new BusinessExcepition("Item de acervo com ID " + id + " não encontrado para deleção");
            }

            switch (tipoItemAcervo) {
                case LIVRO ->
                        livroRepository.delete(id);
                case EBOOK ->
                        ebookRepository.delete(id);
                case PERIODICO ->
                        periodicoRepository.delete(id);
                default ->
                        throw new BusinessExcepition("Tipo de item de acervo desconhecido para deleção.");
            }
        } catch (NumberFormatException e) {
            throw new BusinessExcepition("Formato de ID inválido para deleção: " + id);
        } catch (DbException e) {
            throw new BusinessExcepition("Erro de banco de dados ao deletar item de acervo: " + e.getMessage());
        }
    }

    // Vizualizar itens do acervo

    public ItemAcervo buscarItemPorId (int id) {
        try {

            Livro livro = livroRepository.buscarPorId(id);
            if (livro != null) {return livro;}

            Ebook ebook = ebookRepository.buscarPorId(id);
            if (ebook != null) {return ebook;}

            Periodico periodico = periodicoRepository.buscarPorId(id);
            if (periodico != null) {return periodico;}

            return null;
        }catch (NumberFormatException e) {
            throw new BusinessExcepition ("Formato de ID invalido: " + id);
        }catch (DbException e) {
            throw new BusinessExcepition("Erro ao buscar item de acervo por ID: " + e.getMessage());
        }
    }

    public List<ItemAcervo> listarTodosItens () {
        List<ItemAcervo> todosItens = new ArrayList<>();
        try {
            todosItens.addAll(livroRepository.buscarTodos());
            todosItens.addAll(ebookRepository.buscarTodos());
            todosItens.addAll(periodicoRepository.buscarTodos());
        } catch (DbException e) {
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
        } catch (DbException e) {
            throw new BusinessExcepition("Erro ao buscar itens de acervo por termo: " + e.getMessage());
        }
        return resultados;
    }

    // Gerenciar exemplares de itens do acervo

    public void adicionarExemplares () {}
    public void deletarExemplares () {}
    public void atualizarStatusExemplar () {}
    public List<Exemplar> listarExemplaresPorLivro () {
        return null;
    }
}

