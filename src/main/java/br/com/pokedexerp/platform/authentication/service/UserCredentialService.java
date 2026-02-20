package br.com.pokedexerp.platform.authentication.service;

import br.com.pokedexerp.platform.authentication.model.UserCredentialDto;

import java.util.UUID;

public interface UserCredentialService {
    UserCredentialDto findByEmailAndPassword(String email, String password);

    void changePassword(UUID credentialId, String newPassword);

    void createCredential(UUID userId, String password);
}
