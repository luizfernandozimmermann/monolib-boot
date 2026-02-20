package br.com.pokedexerp.platform.entity.handler.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ListEntityInput {
    private Set<String> fields;
    private String filter;
    private String orderBy;
    @Min(0)
    private int page = 0;
    @Min(1) @Max(200)
    private int size = 10;
}
