package br.com.pokedexerp.platform.messaging.annotation;

import br.com.pokedexerp.platform.messaging.context.WebSocketContext;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import br.com.pokedexerp.platform.messaging.exception.WebsocketExceptionHandler;
import br.com.pokedexerp.platform.metrics.WebSocketMetricsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.atomic.AtomicReference;

@Aspect
@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebsocketInterceptAspect {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    WebsocketExceptionHandler exceptionHandler;

    WebSocketMetricsService metricsService;

    @Around("@annotation(WebsocketIntercept)")
    public Object handleMyAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            var session = getSession(joinPoint.getArgs());
            WebSocketContext.setWebsocketSession(session);
            result = runMethod(joinPoint);
        } catch (ServiceException e) {
            exceptionHandler.handle(e);
        } catch (Exception e) {
            exceptionHandler.handle(new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, e));
        }
        WebSocketContext.clear();
        return result;
    }

    private Object runMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var methodName = joinPoint.getSignature().getName();
        return switch (methodName) {
            case "afterConnectionEstablished" -> {
                metricsService.incrementConnections();
                yield joinPoint.proceed();
            }
            case "afterConnectionClosed" -> {
                metricsService.decrementConnections();
                yield joinPoint.proceed();
            }
            case "handleMessage" -> runMethodHandleMessage(joinPoint);
            default -> joinPoint.proceed();
        };
    }

    private Object runMethodHandleMessage(ProceedingJoinPoint joinPoint) throws Throwable {
        var args = joinPoint.getArgs();
        if (args.length > 1 && args[1] instanceof WebSocketMessage<?> message) {
            try {
                var payload = (String) message.getPayload();
                var json = objectMapper.readTree(payload);
                var event = json.get("event").asText();

                metricsService.trackMessage(event);

                var result = new AtomicReference<>();
                metricsService.trackLatency(event, () -> {
                    try {
                        result.set(joinPoint.proceed());
                    } catch (Throwable e) {
                        throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Error handling message", new Exception(e));
                    }
                });
                return result.get();
            } catch (Exception _) {
                return joinPoint.proceed();
            }
        }
        return joinPoint.proceed();
    }

    private static WebSocketSession getSession(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof WebSocketSession session) {
                return session;
            }
        }
        throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "No session");
    }

}
