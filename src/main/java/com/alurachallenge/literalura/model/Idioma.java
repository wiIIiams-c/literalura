package com.alurachallenge.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Idioma(
    @JsonAlias("lenguaje") String idioma,
    @JsonAlias("codigo") String codIdioma
) {

}
