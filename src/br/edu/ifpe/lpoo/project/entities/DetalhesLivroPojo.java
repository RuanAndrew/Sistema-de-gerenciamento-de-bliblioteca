package br.edu.ifpe.lpoo.project.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Importe esta anotação

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetalhesLivroPojo {
	
	private String title;
    private Integer number_of_pages;
    private String publish_date;
    private List<String> subjects;
    private List<String> publishers;
    private List<AutorPojo> authors;
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getNumber_of_pages() {
		return number_of_pages;
	}
	public void setNumber_of_pages(Integer number_of_pages) {
		this.number_of_pages = number_of_pages;
	}
	public String getPublish_date() {
		return publish_date;
	}
	public void setPublish_date(String publish_date) {
		this.publish_date = publish_date;
	}
	public List<String> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}
	public List<AutorPojo> getAuthors() {
		return authors;
	}
	public void setAuthors(List<AutorPojo> authors) {
		this.authors = authors;
	}
	public List<String> getPublishers() {
		return publishers;
	}
	public void setPublishers(List<String> publishers) {
		this.publishers = publishers;
	}
    
    
}
