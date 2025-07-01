package br.edu.ifpe.lpoo.project.business.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

// Ignora propriedades JSON que não vamos mapear
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetalhesLivroApi {

    @JsonProperty("title")
    private String title;

    // O nome do campo no JSON é "authors" (no plural)
    @JsonProperty("authors")
    private List<AutorApi> authors;

    // O nome do campo no JSON é "publish_date" (com underscore)
    @JsonProperty("publish_date")
    private String publish_date;

    // O nome do campo no JSON é "number_of_pages" (com underscore)
    @JsonProperty("number_of_pages")
    private Integer number_of_pages;

    // O nome do campo no JSON é "subjects" (no plural)
    @JsonProperty("subjects")
    private List<String> subjects;

    // O nome do campo no JSON é "publishers" (no plural)
    @JsonProperty("publishers")
    private List<String> publishers;

    // Você pode adicionar outros campos se precisar, como "key", "cover", etc.

    // --- Getters e Setters (Necessários para o Jackson) ---

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AutorApi> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AutorApi> authors) {
        this.authors = authors;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public Integer getNumber_of_pages() {
        return number_of_pages;
    }

    public void setNumber_of_pages(Integer number_of_pages) {
        this.number_of_pages = number_of_pages;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }
}