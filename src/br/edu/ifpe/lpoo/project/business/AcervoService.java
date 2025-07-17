package br.edu.ifpe.lpoo.project.business;
import br.edu.ifpe.lpoo.project.data.acervo.implement.EbookRepository;
import br.edu.ifpe.lpoo.project.data.acervo.implement.ExemplarRepository;
import br.edu.ifpe.lpoo.project.data.acervo.implement.LivroRepository;
import br.edu.ifpe.lpoo.project.data.acervo.implement.PeriodicoRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.*;
import br.edu.ifpe.lpoo.project.enums.FormatoDigital;
import br.edu.ifpe.lpoo.project.enums.StatusExemplar;
import br.edu.ifpe.lpoo.project.enums.TipoItemAcervo;
import br.edu.ifpe.lpoo.project.exceptions.BusinessException;
import br.edu.ifpe.lpoo.project.exceptions.DbException;
import br.edu.ifpe.lpoo.project.ui.dto.acervo.EbookDTO;
import br.edu.ifpe.lpoo.project.ui.dto.acervo.LivroDTO;
import br.edu.ifpe.lpoo.project.ui.dto.acervo.PeriodicoDTO;

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

    public AcervoService() {
        this.livroRepository = new LivroRepository();
        this.exemplarRepository = new ExemplarRepository();
        this.ebookRepository = new EbookRepository();
        this.periodicoRepository = new PeriodicoRepository();
    }

    // Cadastrar itens ao acervo

    public void cadastrarLivro(String titulo, String autor, String anoPublicacao, String editora, String isbn, String numeroPaginas, String genero, String idioma, String quantidadeExemplares) {

        if (titulo.isBlank()) {
            throw new BusinessException("O título é obrigatório.");
        }
        if (autor.isBlank()) {
            throw new BusinessException("O autor é obrigatório.");
        }
        if (anoPublicacao.isBlank()) {
            throw new BusinessException("O ano de publicação é obrigatório.");
        }
        if (numeroPaginas.isBlank()) {
            throw new BusinessException("O número de páginas é obrigatório.");
        }
        if (genero.isBlank()) {
            throw new BusinessException("O gênero é obrigatório.");
        }
        if (idioma.isBlank()) {
            throw new BusinessException("O idioma é obrigatório.");
        }

        if (titulo.length() > 255) {
            throw new BusinessException("O título não pode ter mais de 255 caracteres.");
        }
        if (autor.length() > 255) {
            throw new BusinessException("O autor não pode ter mais de 255 caracteres.");
        }
        if (editora != null && editora.length() > 255) {
            throw new BusinessException("A editora não pode ter mais de 255 caracteres.");
        }
        if (isbn != null && isbn.length() > 17) {
            throw new BusinessException("O ISBN não pode ter mais de 17 caracteres.");
        }
        if (genero.length() > 255) {
            throw new BusinessException("O gênero não pode ter mais de 255 caracteres.");
        }
        if (idioma.length() > 255) {
            throw new BusinessException("O idioma não pode ter mais de 255 caracteres.");
        }

        if (isValidIsbn(isbn)) {
            throw new BusinessException("ISBN invalido");
        }

        int parsedNumeroPaginas;
        try {
            parsedNumeroPaginas = Integer.parseInt(numeroPaginas);
        } catch (NumberFormatException e) {
            throw new BusinessException("Número de páginas inválido: deve ser um valor numérico.");
        }
        int parsedQuantidadeExemplares;
        try {
            parsedQuantidadeExemplares = Integer.parseInt(quantidadeExemplares);
        } catch (NumberFormatException e) {
            throw new BusinessException("Quantidade de exemplares inválida: deve ser um valor numérico.");
        }
        int parsedAnoPublicacao;
        try {
            parsedAnoPublicacao = Integer.parseInt(anoPublicacao);
        } catch (NumberFormatException e) {
            throw new BusinessException("Ano de publicação inválido: deve ser um valor numérico.");
        }

        if (parsedNumeroPaginas < 1) {
            throw new BusinessException("O número de páginas deve ser maior que zero.");
        }
        if (parsedQuantidadeExemplares < 0) {
            throw new BusinessException("A quantidade de exemplares não pode ser negativa.");
        }
        if (parsedAnoPublicacao > anoAtual) {
            throw new BusinessException("O ano de publicação não pode ser futuro.");
        }

        Pattern anoPublicacaoPattern = Pattern.compile("^\\d{1,4}$");
        Matcher anoPublicacaoMatcher = anoPublicacaoPattern.matcher(anoPublicacao);
        if (!anoPublicacaoMatcher.matches()) {
            throw new BusinessException("O ano deve ter de 1 a quatro digitos.");
        }

        Livro livro = new Livro(titulo,autor, parsedAnoPublicacao, editora, idioma, isbn, parsedNumeroPaginas, genero);
        livroRepository = new LivroRepository();
        exemplarRepository = new ExemplarRepository();

        boolean exist = livroRepository.existItem(livro);

        if(!exist) {
            try {
                livroRepository.insert(livro);
            }catch(DbException e) {
                throw new BusinessException(e.getMessage());
            }

            for (int i = 1; i <= parsedQuantidadeExemplares; i++) {

                try {
                    String registro = livro.getId() + "EXP" + i;
                    ExemplarFisico exemplarFisico = new ExemplarFisico(livro.getId(), TipoItemAcervo.LIVRO, registro, StatusExemplar.DISPONIVEL);
                    exemplarRepository.insert(exemplarFisico, livro.getId());
                }catch (DbException e) {
                    throw new BusinessException(e.getMessage());
                }
            }
        }else {
            throw new BusinessException("Esse livro ja esta cadastrado no sistema");
        }
    }

    public void cadastrarEbook (String titulo,String autor, String anoPublicacao, String editora, String isbn, String numeroPaginas, String genero, String idioma, String formatoDigital, String url, String quantidadeLicenca) {
        if (titulo.isBlank()) {
            throw new BusinessException("O título é obrigatório.");
        }
        if (autor.isBlank()) {
            throw new BusinessException("O autor é obrigatório.");
        }
        if (anoPublicacao.isBlank()) {
            throw new BusinessException("O ano de publicação é obrigatório.");
        }
        if (numeroPaginas.isBlank()) {
            throw new BusinessException("O número de páginas é obrigatório.");
        }
        if (genero.isBlank()) {
            throw new BusinessException("O gênero é obrigatório.");
        }
        if (idioma.isBlank()) {
            throw new BusinessException("O idioma é obrigatório.");
        }
        if (formatoDigital.isBlank()) {
            throw new BusinessException("O formato digital é obrigatório.");
        }
        if (url.isBlank()) {
            throw new BusinessException("A URL e obrigatória.");
        }

        if (isValidIsbn(isbn)) {
            throw new BusinessException("ISBN invalido");
        }

        int parsedNumeroPaginas;
        try {
            parsedNumeroPaginas = Integer.parseInt(numeroPaginas);
        } catch (NumberFormatException e) {
            throw new BusinessException("Número de páginas inválido: deve ser um valor numérico.");
        }
        int parsedAnoPublicacao;
        try {
            parsedAnoPublicacao = Integer.parseInt(anoPublicacao);
        } catch (NumberFormatException e) {
            throw new BusinessException("Ano de publicação inválido: deve ser um valor numérico.");
        }
        int parsedQuantidadeLiceca;
        try {
            parsedQuantidadeLiceca = Integer.parseInt(quantidadeLicenca);
        } catch (NumberFormatException e) {
            throw new BusinessException("Quantidade de liceças inválida: deve ser um valor numérico.");
        }

        if (parsedNumeroPaginas < 0) {
            throw new BusinessException("O número de páginas não pode ser menor que zero.");
        }

        if (parsedAnoPublicacao > anoAtual) {
            throw new BusinessException("O ano de publicação não pode ser futuro.");
        }
        if (parsedQuantidadeLiceca < 0) {
            throw new BusinessException("A quantidade de liceças não pode ser negativa.");
        }

        Pattern anoPublicacaoPattern = Pattern.compile("-?\\d{1,4}");
        Matcher anoPublicacaoMatcher = anoPublicacaoPattern.matcher(anoPublicacao);
        if (!anoPublicacaoMatcher.matches()) {
            throw new BusinessException("O ano deve ter de 1 a quatro digitos.");
        }

        FormatoDigital formatoDigitalNovo = null;
        for(FormatoDigital c : FormatoDigital.values()) {
            if (formatoDigital.toUpperCase().equals(String.valueOf(c))) {
                formatoDigitalNovo = c;
                break;
            }
        }
        if(formatoDigitalNovo == null) {
            throw new BusinessException("Formato digital invalido.");
        }

        Ebook ebook = new Ebook(titulo,autor,parsedAnoPublicacao,editora,idioma,isbn,parsedNumeroPaginas,genero, formatoDigitalNovo,url);

        boolean exist = ebookRepository.existItem(ebook);

        if(!exist) {
            try {
            	ebookRepository.insert(ebook);
            }catch(DbException e) {
                throw new BusinessException(e.getMessage());
            }

            for (int i = 1; i <= parsedQuantidadeLiceca; i++) {

                try {
                    String registro = ebook.getId() + "EXP" + i;
                    ExemplarDigital exemplarDigital = new ExemplarDigital(ebook.getId(), TipoItemAcervo.LIVRO, registro, StatusExemplar.DISPONIVEL);
                    exemplarRepository.insert(exemplarDigital, ebook.getId());
                }catch (DbException e) {
                    throw new BusinessException(e.getMessage());
                }
            }
        }else {
            throw new BusinessException("Esse ebook ja esta cadastrado no sistema");
        }
    }

    public void cadastrarPeriodico (String titulo,String autor, String anoPublicacao, String issn, String editora, String numeroEdicao, String volume, String genero, String idioma, String quantidadeExemplares) {
        if (titulo.isBlank()) {
            throw new BusinessException("O titulo e obrigatório");
        }
        if (autor.isBlank()) {
            throw  new BusinessException("O autor e obrigatório");
        }
        if (numeroEdicao.isBlank()) {
            throw new BusinessException("O numero da edição e obrigatório");
        }
        if (volume.isBlank()) {
            throw new BusinessException("O volume e obrigatório");
        }
        if (anoPublicacao.isBlank()) {
            throw new BusinessException("O ano de publicação e obrigatório");
        }
        if (genero.isBlank()) {
            throw new BusinessException("O genero e obrigatório");
        }
        if (idioma.isBlank()) {
            throw new BusinessException("O idioma e obrigatório");
        }

        int parsedAnoPublicacao;
        try {
            parsedAnoPublicacao = Integer.parseInt(anoPublicacao);
        } catch (NumberFormatException e) {
            throw new BusinessException("Ano de publicação inválido: deve ser um valor numérico.");
        }
        int parsedNumeroEdicao;
        try {
            parsedNumeroEdicao = Integer.parseInt(numeroEdicao);
        } catch (NumberFormatException e) {
            throw new BusinessException("Numero de edição inválido: deve ser um valor numérico.");
        }
        int parsedVolume;
        try {
            parsedVolume = Integer.parseInt(volume);
        } catch (NumberFormatException e) {
            throw new BusinessException("Volume inválido: deve ser um valor numérico.");
        }
        int parsedQuantidadeExemplares;
        try {
            parsedQuantidadeExemplares = Integer.parseInt(quantidadeExemplares);
        } catch (NumberFormatException e) {
            throw new BusinessException("Quantidade de exemplares inválida: deve ser um valor numérico.");
        }

        if (parsedAnoPublicacao > anoAtual) {
            throw new BusinessException("O ano de publicação não pode ser futuro.");
        }
        if (parsedNumeroEdicao < 0) {
            throw new BusinessException("O numero da edição não pode ser menor que zero");
        }
        if (parsedVolume < 0) {
            throw new BusinessException("O volume não pode ser menor que zero");
        }
        if (parsedQuantidadeExemplares < 0) {
            throw new BusinessException("A quantidade de exemplares não pode ser negativa.");
        }

        Pattern issnPattern = Pattern.compile("^\\d{4}[ -]?\\d{3}[\\dxX]$");
        Matcher issnMatcher = issnPattern.matcher(issn);
        if (!issnMatcher.matches()) {
            throw new BusinessException("ISSN invalido");
        }

        Pattern anoPublicacaoPattern = Pattern.compile("-?\\d{1,4}");
        Matcher anoPublicacaoMatcher = anoPublicacaoPattern.matcher(anoPublicacao);
        if (!anoPublicacaoMatcher.matches()) {
            throw new BusinessException("O ano deve ter de um a quatro digitos.");
        }
      

        Periodico periodico = new Periodico (titulo, autor, parsedAnoPublicacao, editora, idioma, issn, parsedNumeroEdicao, parsedVolume, genero);

        boolean exist = periodicoRepository.existItem(periodico);

        if(!exist) {
            try {
                periodicoRepository.insert(periodico);
            }catch(DbException e) {
                throw new BusinessException(e.getMessage());
            }

            for (int i = 1; i <= parsedQuantidadeExemplares; i++) {

                try {
                    String registro = periodico.getId() + "EXP" + i;
                    ExemplarFisico exemplarFisico = new ExemplarFisico(periodico.getId(), TipoItemAcervo.LIVRO, registro, StatusExemplar.DISPONIVEL);
                    exemplarRepository.insert(exemplarFisico, periodico.getId());
                }catch (DbException e) {
                    throw new BusinessException(e.getMessage());
                }
            }
        }else {
            throw new BusinessException("Esse periodico ja esta cadastrado no sistema");
        }
        
    }

    public Livro buscarDadosParaPreenchimentoAutomatico(String isbn) {
        return catalogacaoService.buscarDadosLivroPorIsbn(isbn);
    }

    // Atualizar itens do acervo

    public void atualizarLivro (LivroDTO dto) {
        if (dto == null) {
            throw new BusinessException("Dados de atualização de livro inválidos: ID não fornecido.");
        }

        int numeroPaginas;
        try {
            numeroPaginas = Integer.parseInt(dto.getNumeroPaginas());
        } catch (NumberFormatException e) {
            throw new BusinessException("Número de páginas inválido: deve ser um valor numérico.");
        }
        int anoPublicacao;
        try {
            anoPublicacao = Integer.parseInt(dto.getAnoPublicacao());
        } catch (NumberFormatException e) {
            throw new BusinessException("Ano de publicação inválido: deve ser um valor numérico.");
        }

        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new BusinessException("O título do livro não pode ser vazio na atualização.");
        }
        if (dto.getAutor() == null || dto.getAutor().isBlank()) {
            throw new BusinessException("O autor do livro não pode ser vazio na atualização.");
        }
        if (dto.getAnoPublicacao() == null || dto.getAnoPublicacao().isBlank()) {
            throw new BusinessException("O ano de publicação do livro não pode ser vazio na atualização.");
        }
        if (dto.getNumeroPaginas() == null || dto.getNumeroPaginas().isBlank()) {
            throw new BusinessException("O numero de paginas do livro não pode ser vazio na atualização.");
        }
        if (dto.getGenero() == null || dto.getGenero().isBlank()) {
            throw new BusinessException("O genero do livro não pode ser vazio na atualização.");
        }
        if (dto.getIdioma() == null || dto.getIdioma().isBlank()) {
            throw new BusinessException("O idioma do livro não pode ser vazio na atualização.");
        }


        try {
            Livro livroAtualNoDB = livroRepository.buscarPorId(dto.getId());

            if (livroAtualNoDB == null) {
                throw new BusinessException("Livro com ID " + dto.getId() + " não encontrado para atualização.");
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
            throw new BusinessException("Erro de banco de dados ao atualizar livro: " + e.getMessage());
        }
    }

    public void atualizarEbook (EbookDTO dto) {
        if (dto == null) {
            throw new BusinessException("Dados de atualização do ebook inválidos: ID não fornecido.");
        }

        int numeroPaginas;
        try {
            numeroPaginas = Integer.parseInt(dto.getNumeroPaginas());
        } catch (NumberFormatException e) {
            throw new BusinessException("Número de páginas inválido: deve ser um valor numérico.");
        }
        int anoPublicacao;
        try {
            anoPublicacao = Integer.parseInt(dto.getAnoPublicacao());
        } catch (NumberFormatException e) {
            throw new BusinessException("Ano de publicação inválido: deve ser um valor numérico.");
        }

        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new BusinessException("O título do ebook não pode ser vazio na atualização.");
        }
        if (dto.getAutor() == null || dto.getAutor().isBlank()) {
            throw new BusinessException("O autor do ebook não pode ser vazio na atualização.");
        }
        if (dto.getAnoPublicacao() == null || dto.getAnoPublicacao().isBlank()) {
            throw new BusinessException("O ano de publicação do ebook não pode ser vazio na atualização.");
        }
        if (dto.getNumeroPaginas() == null || dto.getNumeroPaginas().isBlank()) {
            throw new BusinessException("O numero de paginas do ebook não pode ser vazio na atualização.");
        }
        if (dto.getGenero() == null || dto.getGenero().isBlank()) {
            throw new BusinessException("O genero do ebook não pode ser vazio na atualização.");
        }
        if (dto.getIdioma() == null || dto.getIdioma().isBlank()) {
            throw new BusinessException("O idioma do ebook não pode ser vazio na atualização.");
        }
        if (dto.getUrl() == null || dto.getUrl().isBlank()) {
            throw new BusinessException("A url do ebook não pode ser vazio na atualização.");
        }
        if (dto.getFormatoDigital() == null || dto.getFormatoDigital().isBlank()) {
            throw new BusinessException("O formato digital do ebook não pode ser vazio na atualização.");
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
                throw new BusinessException("Ebook com ID " + dto.getId() + " não encontrado para atualização.");
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
            throw new BusinessException("Erro de banco de dados ao atualizar ebook: " + e.getMessage());
        }
    }

    public void atualizarPeriodico (PeriodicoDTO dto) {
        if (dto == null) {
            throw new BusinessException("Dados de atualização do periodico inválidos: ID não fornecido.");
        }

        int numeroEdicao;
        try {
            numeroEdicao = Integer.parseInt(dto.getNumeroEdicao());
        } catch (NumberFormatException e) {
            throw new BusinessException("Número da edição inválido: deve ser um valor numérico.");
        }
        int anoPublicacao;
        try {
            anoPublicacao = Integer.parseInt(dto.getAnoPublicacao());
        } catch (NumberFormatException e) {
            throw new BusinessException("Ano de publicação inválido: deve ser um valor numérico.");
        }

        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new BusinessException("O título do periodico não pode ser vazio na atualização.");
        }
        if (dto.getAutor() == null || dto.getAutor().isBlank()) {
            throw new BusinessException("O autor do periodico não pode ser vazio na atualização.");
        }
        if (dto.getAnoPublicacao() == null || dto.getAnoPublicacao().isBlank()) {
            throw new BusinessException("O ano de publicação do periodico não pode ser vazio na atualização.");
        }
        if (dto.getNumeroEdicao() == null || dto.getNumeroEdicao().isBlank()) {
            throw new BusinessException("O numero da edição do periodico não pode ser vazio na atualização.");
        }
        if (dto.getGenero() == null || dto.getGenero().isBlank()) {
            throw new BusinessException("O genero do periodico não pode ser vazio na atualização.");
        }
        if (dto.getIdioma() == null || dto.getIdioma().isBlank()) {
            throw new BusinessException("O idioma do periodico não pode ser vazio na atualização.");
        }
        if (dto.getVolume() == null || dto.getVolume().isBlank()) {
            throw new BusinessException("O volume do periodico não pode ser vazio na atualização.");
        }


        try {
            Periodico periodicoAtualNoDB = periodicoRepository.buscarPorId(dto.getId());

            if (periodicoAtualNoDB == null) {
                throw new BusinessException("periodico com ID " + dto.getId() + " não encontrado para atualização.");
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
            throw new BusinessException("Erro de banco de dados ao atualizar periodico: " + e.getMessage());
        }
    }

    // Deletar itens do acervo

    public void deletarItem (Integer id, TipoItemAcervo tipoItemAcervo) {
        if (id == null) {
            throw new BusinessException("ID do item não pode ser nulo ou vazio para deleção.");
        }
        try {
            ItemAcervo itemParaDeletar = buscarItemPorId(id);

            if (itemParaDeletar == null) {
                throw new BusinessException("Item de acervo com ID " + id + " não encontrado para deleção");
            }

            switch (tipoItemAcervo) {
                case LIVRO ->
                        livroRepository.delete(id);
                case EBOOK ->
                        ebookRepository.delete(id);
                case PERIODICO ->
                        periodicoRepository.delete(id);
                default ->
                        throw new BusinessException("Tipo de item de acervo desconhecido para deleção.");
            }
        } catch (NumberFormatException e) {
            throw new BusinessException("Formato de ID inválido para deleção: " + id);
        } catch (DbException e) {
            throw new BusinessException("Erro de banco de dados ao deletar item de acervo: " + e.getMessage());
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
            throw new BusinessException("Formato de ID invalido: " + id);
        }catch (DbException e) {
            throw new BusinessException("Erro ao buscar item de acervo por ID: " + e.getMessage());
        }
    }

    public List<ItemAcervo> listarTodosItens () {
        List<ItemAcervo> todosItens = new ArrayList<>();
        try {
            todosItens.addAll(livroRepository.buscarTodos());
            todosItens.addAll(ebookRepository.buscarTodos());
            todosItens.addAll(periodicoRepository.buscarTodos());
        } catch (DbException e) {
            throw new BusinessException("Erro ao listar todos os itens do acervo: " + e.getMessage());
        }
        return todosItens;
    }

    public List<ItemAcervo> buscarItensPorTermo (String termoBusca) {

        if (termoBusca == null || termoBusca.isBlank()) {
            throw new BusinessException("O termo de pesquisa não pode ser vazio");
        }

        List<ItemAcervo> resultados = new ArrayList<>();
        try {
            resultados.addAll(livroRepository.buscarPorTermo(termoBusca));
            resultados.addAll(ebookRepository.buscarPorTermo(termoBusca));
            resultados.addAll(periodicoRepository.buscarPorTermo(termoBusca));
        } catch (DbException e) {
            throw new BusinessException("Erro ao buscar itens de acervo por termo: " + e.getMessage());
        }
        return resultados;
    }

    // Gerenciar exemplares de itens do acervo

    public void adicionarExemplares(int idItem, String quantidadeAdicionar) {
        int parsedQuantidadeAdicionar;
        try {
            parsedQuantidadeAdicionar = Integer.parseInt(quantidadeAdicionar);
        } catch (NumberFormatException e) {
            throw new BusinessException("A quantidade de exemplares a adicionar deve ser um valor numérico.");
        }

        if (parsedQuantidadeAdicionar <= 0) {
            throw new BusinessException("A quantidade de exemplares a adicionar deve ser maior que zero.");
        }

        Object itemEncontrado = null;
        TipoItemAcervo tipoItem = null;

        if (livroRepository.existPorId(idItem)) {
            itemEncontrado = livroRepository.buscarPorId(idItem);
            tipoItem = TipoItemAcervo.LIVRO;
        } else if (periodicoRepository.existPorId(idItem)) {
            itemEncontrado = periodicoRepository.buscarPorId(idItem);
            tipoItem = TipoItemAcervo.PERIODICO;
        } else if (ebookRepository.existPorId(idItem)) {
            itemEncontrado = ebookRepository.buscarPorId(idItem);
            tipoItem = TipoItemAcervo.EBOOK;
        }

        if (itemEncontrado == null) {
            throw new BusinessException("Item com ID " + idItem + " não encontrado.");
        }

        int maiorRegistro = exemplarRepository.getMaiorRegistro();

        for (int i = 1; i <= parsedQuantidadeAdicionar; i++) {
            String registro = idItem + "EXP" + (maiorRegistro + i);

            Exemplar exemplar = new Exemplar(idItem, tipoItem, registro, StatusExemplar.DISPONIVEL);

            exemplarRepository.insert(exemplar, idItem);
        }
    }

    public void deletarExemplares (int idExemplar) {
        boolean exist = exemplarRepository.existPorId(idExemplar);

        if (exist) {
            exemplarRepository.delete(idExemplar);
        }else {
            throw new BusinessException("Exemplar com ID " + idExemplar + " não encontrado para exclusão.");
        }
    }

    public void atualizarStatusExemplar (int idExemplar, StatusExemplar statusExemplar) {
        boolean exist = exemplarRepository.existPorId(idExemplar);

        if (exist) {
            exemplarRepository.atualizarStatus(idExemplar, statusExemplar);
        }else {
            throw new BusinessException("Exemplar com ID " + idExemplar + " não encontrado para atualização de status.");
        }
    }

    public List<Exemplar> listarExemplaresPorItem (int idItem) {

        boolean itemExists = livroRepository.existPorId(idItem) ||
                periodicoRepository.existPorId(idItem) ||
                ebookRepository.existPorId(idItem);


        if (!itemExists) {
            throw new BusinessException("Item com ID " + idItem + " não encontrado.");
        }

        return exemplarRepository.buscarTodosPorIdLivro(idItem);

    }

    public List<Exemplar> listarExemplaresPorTermo (String termoBusca) {

        if (termoBusca == null || termoBusca.isBlank()) {
            throw new BusinessException("O termo de pesquisa não pode ser vazio");
        }

        return exemplarRepository.buscarPorTermo(termoBusca);
    }

    // metodos auxiliares

    public static boolean isValidIsbn(String isbn) {

        final String ISBN10_REGEX = "^\\d{9}[\\dX]$";
        final String ISBN13_REGEX = "^(978|979)\\d{10}$";

        if (isbn == null || isbn.trim().isEmpty()) {
            return true;
        }

        String cleanedIsbn = isbn.trim().replaceAll("[\\s-]", "");

        if (cleanedIsbn.length() == 10) {
            return !Pattern.matches(ISBN10_REGEX, cleanedIsbn);
        } else if (cleanedIsbn.length() == 13) {
            return !Pattern.matches(ISBN13_REGEX, cleanedIsbn);
        }

        return true;
    }
}

