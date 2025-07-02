package br.edu.ifpe.lpoo.project.business.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Ignora propriedades adicionais do autor que não precisamos
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorApi {

    @JsonProperty("name")
    private String name;

    // O campo "key" do JSON pode ser útil se você precisar de um ID do autor
    @JsonProperty("key")
    private String key;

    // --- Getters e Setters ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}