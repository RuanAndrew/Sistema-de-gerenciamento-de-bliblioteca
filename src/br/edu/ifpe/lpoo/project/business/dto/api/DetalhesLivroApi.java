package br.edu.ifpe.lpoo.project.business.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetalhesLivroApi {

    @JsonProperty("title")
    private String title;

    @JsonProperty("authors")
    private List<AutorApi> authors;

    @JsonProperty("publish_date")
    private String publish_date;

    @JsonProperty("number_of_pages")
    private Integer number_of_pages;

    @JsonProperty("subjects")
    private List<String> subjects;

    @JsonProperty("publishers")
    private List<String> publishers;


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