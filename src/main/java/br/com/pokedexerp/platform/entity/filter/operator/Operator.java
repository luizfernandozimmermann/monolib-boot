package br.com.pokedexerp.platform.entity.filter.operator;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum Operator {

    GREATER_OR_EQUAL(">="),
    LESS_OR_EQUAL("<="),
    NOT_EQUAL("!="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    EQUAL("="),

    NOT_LIKE("NOT", "LIKE"),
    LIKE("LIKE"),

    NOT_IN("NOT", "IN"),
    IN("IN"),

    IS_NOT_NULL("IS", "NOT", "NULL"),
    IS_NULL("IS", "NULL");


    private static final List<Operator> ORDERED = Arrays.stream(values())
            .sorted((a, b) -> Integer.compare(tokenWeight(b), tokenWeight(a)))
            .toList();

    @Getter
    private final List<String> tokens;

    Operator(String... tokens) {
        this.tokens = List.of(tokens);
    }

    public static List<Operator> orderedValues() {
        return ORDERED;
    }

    private static int tokenWeight(Operator op) {
        return op.tokens.stream()
                .mapToInt(String::length)
                .sum();
    }

}
