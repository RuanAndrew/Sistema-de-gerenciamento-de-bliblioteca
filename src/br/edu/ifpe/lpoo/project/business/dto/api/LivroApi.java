package br.edu.ifpe.lpoo.project.business.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Ignora quaisquer propriedades JSON que não tenham campos correspondentes na classe
@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroApi {

    // O campo "details" do JSON contém os dados que precisamos
    @JsonProperty("details")
    private DetalhesLivroApi details;

    public DetalhesLivroApi getDetails() {
        return details;
    }

    public void setDetails(DetalhesLivroApi details) {
        this.details = details;
    }
}