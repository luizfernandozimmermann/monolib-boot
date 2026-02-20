package br.com.pokedexerp.platform.entity.filter.parser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "input")
public class ParseValue {

    private final String input;
    @Setter
    private int pos = 0;

    public static ParseValue of(String input) {
        return new ParseValue(input);
    }

    public ParseValue(String input) {
        this.input = input;
    }

    public int increment() {
        return pos++;
    }

    public String getCurrent() {
        return String.valueOf(input.charAt(pos));
    }

    public void skipWhiteSpace() {
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
            pos++;
        }
    }

    public boolean match(String token) {
        skipWhiteSpace();
        if (input.startsWith(token, getPos())) {
            pos += token.length();
            return true;
        }
        return false;
    }

    public boolean matchKeyword(String keyword) {
        skipWhiteSpace();
        if (input.regionMatches(true, getPos(), keyword, 0, keyword.length())) {
            pos += keyword.length();
            return true;
        }
        return false;
    }

    public void expect(String token) {
        if (!match(token)) {
            throw new IllegalArgumentException("Expected '" + token + "'");
        }
    }

    public boolean peek(String token) {
        skipWhiteSpace();
        return input.startsWith(token, pos);
    }

}
