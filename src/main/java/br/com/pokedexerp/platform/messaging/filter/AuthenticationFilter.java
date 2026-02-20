package br.com.pokedexerp.platform.messaging.filter;

import br.com.pokedexerp.platform.authentication.service.AuthenticationService;
import br.com.pokedexerp.platform.messaging.context.RequestContext;
import br.com.pokedexerp.platform.messaging.context.ServiceContext;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import br.com.pokedexerp.platform.messaging.utils.AttributeConstants;
import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Order(1)
@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements Filter {

    AuthenticationService authenticationService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) {
        setSession();
        log.info("{} - Handling message - {}", ServiceContext.getEmail(), ((HttpServletRequest) request).getRequestURI());
        process(request, response, filter);
    }

    private static void process(ServletRequest request, ServletResponse response, FilterChain filter) {
        try {
            filter.doFilter(request, response);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("Filter error: ", e);
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private void setSession() {
        var accessToken = ServiceContext.getCookie(AttributeConstants.COOKIE_TOKEN);
        if (!StringUtil.isNullOrEmpty(accessToken)) {
            try {
                RequestContext.setSession(authenticationService.authenticateByAccessToken(accessToken));
            } catch (Exception e) {
                log.error("Error authenticating: ", e);
            }
        }
    }

}
