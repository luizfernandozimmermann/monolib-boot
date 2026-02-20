package br.com.pokedexerp.platform.authentication.handler;

import br.com.pokedexerp.platform.authentication.handler.dto.ChangePasswordInput;
import br.com.pokedexerp.platform.authentication.handler.dto.ChangePasswordOutput;
import br.com.pokedexerp.platform.messaging.annotation.PostRequest;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("authentication")
public interface ChangePasswordHandler {

    @PostRequest(path = "/changePassword", authenticateFirstSession = false, permissions = "teste.aaaa")
    ChangePasswordOutput changePassword(ChangePasswordInput input);

}
