package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.entities.acervo.Ebook;
import br.edu.ifpe.lpoo.project.entities.acervo.Livro;
import br.edu.ifpe.lpoo.project.business.dto.api.AutorApi;
import br.edu.ifpe.lpoo.project.business.dto.api.DetalhesLivroApi;
import br.edu.ifpe.lpoo.project.business.dto.api.LivroApi;

import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class CatalogacaoService {

    private static final String OPEN_LIBRARY_API_URL  = "https://openlibrary.org/api/books";
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;


    public CatalogacaoService() {
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClient.newHttpClient();
    }

    private String sendApiRequest(String isbn) throws BusinessExcepition {
        String url = OPEN_LIBRARY_API_URL + "?bibkeys=ISBN:"+ isbn +"&format=json&jscmd=details";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new BusinessExcepition("Erro na requisição HTTP para Open Library. Código: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new BusinessExcepition("Erro ao consumir a API Open Library: " + e.getMessage());
        }
    }

    public Livro buscarDadosLivroPorIsbn(String isbn) throws BusinessExcepition {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new BusinessExcepition("ISBN não pode ser nulo ou vazio para busca de dados.");
        }

        String jsonResponse = sendApiRequest(isbn);
        if (jsonResponse.isEmpty() || jsonResponse.equals("{}")) {
            return null;
        }

        return parseJsonResponseLivro(jsonResponse, isbn);
    }

    private Livro parseJsonResponseLivro(String json, String isbn) throws BusinessExcepition {
        try {
            Map<String, LivroApi> livroMap = objectMapper.readValue(json, objectMapper.getTypeFactory().constructMapType(Map.class, String.class, LivroApi.class));
            LivroApi livroApi = livroMap.get("ISBN:" + isbn);

            if (livroApi != null && livroApi.getDetails() != null) {
                DetalhesLivroApi detalhesLivro = livroApi.getDetails();

                String titulo = detalhesLivro.getTitle();

                List<String> nomeAutoresList = new ArrayList<>();
                if (detalhesLivro.getAuthors() != null && !detalhesLivro.getAuthors().isEmpty()) {
                    for (AutorApi autor : detalhesLivro.getAuthors()) {
                        nomeAutoresList.add(autor.getName());
                    }
                }
                String[] autoresArray = nomeAutoresList.toArray(new String[0]);

                List<String> generosList = detalhesLivro.getSubjects() != null ? detalhesLivro.getSubjects() : new ArrayList<>();
                String[] generosArray = generosList.toArray(new String[0]);

                List<String> editorasList = detalhesLivro.getPublishers() != null ? detalhesLivro.getPublishers() : new ArrayList<>();
                String[] editorasArray = editorasList.toArray(new String[0]);

                int numeroPaginas = detalhesLivro.getNumber_of_pages() != null ? detalhesLivro.getNumber_of_pages() : 0;
                String anoPublicacao = null;
                if (detalhesLivro.getPublish_date() != null && !detalhesLivro.getPublish_date().isEmpty()) {
                    String fullDate = detalhesLivro.getPublish_date();
                    if (fullDate.matches(".*\\d{4}.*")) {
                        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(\\d{4})").matcher(fullDate);
                        if (matcher.find()) {
                            anoPublicacao = matcher.group(1);
                        }
                    } else {
                        anoPublicacao = fullDate;
                    }
                }

                String[] idioma = new String[]{"Português"};

                assert anoPublicacao != null;
                return new Livro(titulo, Arrays.toString(autoresArray), Integer.parseInt(anoPublicacao), Arrays.toString(editorasArray), Arrays.toString(idioma), isbn, numeroPaginas, Arrays.toString(generosArray));
            }
        } catch (JsonProcessingException e) {
            throw new BusinessExcepition("Erro ao parsear JSON da API de catalogação: " + e.getMessage());
        } catch (Exception e) {
            throw new BusinessExcepition("Erro inesperado ao processar dados da API de catalogação: " + e.getMessage());
        }
        return null;
    }

    public Ebook buscarDadosEbookPorIsbn(String isbn) throws BusinessExcepition {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new BusinessExcepition("ISBN não pode ser nulo ou vazio para busca de dados de E-book.");
        }

        String jsonResponse = sendApiRequest(isbn);
        if (jsonResponse.isEmpty() || jsonResponse.equals("{}")) {
            return null; // Nenhuma informação encontrada para o ISBN na API
        }

        return parseJsonResponseEbook(jsonResponse, isbn);
    }

    private Ebook parseJsonResponseEbook(String json, String isbn) throws BusinessExcepition {
        try {
            Map<String, LivroApi> livroMap = objectMapper.readValue(json, objectMapper.getTypeFactory().constructMapType(Map.class, String.class, LivroApi.class));
            LivroApi livroApi = livroMap.get("ISBN:" + isbn);

            if (livroApi != null && livroApi.getDetails() != null) {
                DetalhesLivroApi detalhesLivro = livroApi.getDetails();

                String titulo = detalhesLivro.getTitle();

                List<String> nomeAutoresList = new ArrayList<>();
                if (detalhesLivro.getAuthors() != null && !detalhesLivro.getAuthors().isEmpty()) {
                    for (AutorApi autor : detalhesLivro.getAuthors()) {
                        nomeAutoresList.add(autor.getName());
                    }
                }
                String[] autoresArray = nomeAutoresList.toArray(new String[0]);

                List<String> generosList = detalhesLivro.getSubjects() != null ? detalhesLivro.getSubjects() : new ArrayList<>();
                String[] generosArray = generosList.toArray(new String[0]);

                List<String> editorasList = detalhesLivro.getPublishers() != null ? detalhesLivro.getPublishers() : new ArrayList<>();
                String[] editorasArray = editorasList.toArray(new String[0]);

                int numeroPaginas = detalhesLivro.getNumber_of_pages() != null ? detalhesLivro.getNumber_of_pages() : 0;
                String anoPublicacao = null;
                if (detalhesLivro.getPublish_date() != null && !detalhesLivro.getPublish_date().isEmpty()) {
                    String fullDate = detalhesLivro.getPublish_date();
                    if (fullDate.matches(".*\\d{4}.*")) {
                        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(\\d{4})").matcher(fullDate);
                        if (matcher.find()) {
                            anoPublicacao = matcher.group(1);
                        }
                    } else {
                        anoPublicacao = fullDate;
                    }
                }

                String[] idioma = new String[]{"Português"};

                assert anoPublicacao != null;
                return new Ebook(titulo, Arrays.toString(autoresArray), Integer.parseInt(anoPublicacao), Arrays.toString(editorasArray), Arrays.toString(idioma), isbn, numeroPaginas, Arrays.toString(generosArray), null, null);
            }

        } catch (JsonProcessingException e) {
            throw new BusinessExcepition("Erro ao parsear JSON da API de catalogação para E-book: " + e.getMessage());
        } catch (Exception e) {
            throw new BusinessExcepition("Erro inesperado ao processar dados da API de catalogação para E-book: " + e.getMessage());
        }
        return null;
    }

    public Livro buscarDadosPeriodicoPorIssn(String issn) throws BusinessExcepition {
        return null;
    }

    private String sendApiRequestPeriodico(String issn) throws BusinessExcepition {
        return issn;
    }

    private Livro parseJsonResponsePeriodico(String json, String issn) throws BusinessExcepition {
        return null;
    }
}