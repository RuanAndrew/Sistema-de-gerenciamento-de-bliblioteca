package br.edu.ifpe.lpoo.project.business;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifpe.lpoo.project.entities.AutorPojo;
import br.edu.ifpe.lpoo.project.entities.DetalhesLivroPojo;
import br.edu.ifpe.lpoo.project.entities.Livro;
import br.edu.ifpe.lpoo.project.entities.LivroPojo;

public class ControllerSearchIsbn {
	
	private static final String OPEN_LIBRARY_API_URL  = "https://openlibrary.org/api/books";
	
	public static String searchBookByIsbn(String isbn) {	
		//Documentação: https://openlibrary.org/dev/docs/api/books
		
		if(isbn == null || isbn.trim().isEmpty()) {
			System.err.println("ISBN não pode ser nulo ou vazio.");
			return "";
		}
		
		String url = OPEN_LIBRARY_API_URL + "?bibkeys=ISBN:"+ isbn +"&format=json&jscmd=details";
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
		
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if(response.statusCode() == 200) {
				return response.body();
			}else {
				System.err.println("Erro na requisição HTTP. Código: " + response.statusCode());
				return "";
			}	
		}catch(Exception e) {
			System.err.println("Erro ao consumir a API: " + e.getMessage());
		}
		return "";
	}
	
	
	public static Livro completeFields (String json, String isbn) {
		
		if(json == null || json.trim().isEmpty() || json.equals("{}")) {
			System.err.println("JSON inválido ou vazio para preencher campos.");
			return null;
		}
		
		ObjectMapper mapeando = new ObjectMapper();
		
		try {
			Map<String, LivroPojo> livroMap = mapeando.readValue(json, mapeando.getTypeFactory().constructMapType(Map.class, String.class, LivroPojo.class));
			LivroPojo livroPojo = livroMap.get("ISBN:" + isbn);
			
			if(livroPojo != null && livroPojo.getDetails() != null) {
				DetalhesLivroPojo detalhesLivro = livroPojo.getDetails();
				String titulo = detalhesLivro.getTitle();
				
				
				List<String> nomeAutores = new ArrayList<>();
				if(detalhesLivro.getAuthors() != null &&!detalhesLivro.getAuthors().isEmpty()) {
					for(AutorPojo autor : detalhesLivro.getAuthors()) {
						nomeAutores.add(autor.getName());
					}
				}
				
				String autores = juntar(nomeAutores, "-");
				String generos = juntar(detalhesLivro.getSubjects(), "-");
				String editoras = juntar(detalhesLivro.getPublishers(), "-");
				
				int paginas = detalhesLivro.getNumber_of_pages() != null ? detalhesLivro.getNumber_of_pages() : 0;
				String dataPublicacao = detalhesLivro.getPublish_date();
				
				
				return new Livro (isbn, titulo, autores, editoras, paginas, dataPublicacao, generos);
			}
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static String juntar(List<String> lista, String delimitador) {
		if(lista == null || lista.isEmpty()) {
			return "";
		}
		
		StringJoiner juntar = new StringJoiner(delimitador);
		for(String elemento : lista) {
			juntar.add(elemento);
		}
		return juntar.toString();
	}
}
