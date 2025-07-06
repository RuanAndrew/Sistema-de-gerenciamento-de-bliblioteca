package br.edu.ifpe.lpoo.project.business.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroApi {

    @JsonProperty("details")
    private DetalhesLivroApi details;

    public DetalhesLivroApi getDetails() {
        return details;
    }

    public void setDetails(DetalhesLivroApi details) {
        this.details = details;
    }
}