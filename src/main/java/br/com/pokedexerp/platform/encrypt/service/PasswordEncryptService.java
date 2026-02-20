package br.com.pokedexerp.platform.encrypt.service;

public interface PasswordEncryptService {

    String hash(String value);
    boolean verify(String hash, String value);

}
