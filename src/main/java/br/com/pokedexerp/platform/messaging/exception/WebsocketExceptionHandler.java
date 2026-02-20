package br.com.pokedexerp.platform.messaging.exception;

import br.com.pokedexerp.platform.messaging.context.WebSocketContext;
import br.com.pokedexerp.platform.messaging.model.ExceptionResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebsocketExceptionHandler {

    private static final ObjectWriter objectWriter = new ObjectMapper().registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer().withDefaultPrettyPrinter();

    public void handle(ServiceException e) {
        sendErrorMessage(WebSocketContext.getWebsocketSession(), e);
    }

    @SneakyThrows
    private static void sendErrorMessage(WebSocketSession session, ServiceException e) {
        session.sendMessage(new TextMessage(objectWriter.writeValueAsString(new ExceptionResponseDto(e))));
    }

}
