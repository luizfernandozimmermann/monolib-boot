package monolib.web.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import monolib.core.context.ContextHolder;
import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.web.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(1)
@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestFilter implements Filter {

    RequestContext requestContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) {
        requestContext.register();
        var context = (RequestContext) ContextHolder.get();
        log.info("{} - Handling message - {}", context.getEmail(), context.getRequestPath());
        process(request, response, filter);
    }

    private static void process(ServletRequest request, ServletResponse response, FilterChain filter) {
        try {
            filter.doFilter(request, response);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error: ", e);
            throw new BusinessException(ErrorCode.UNAUTHORIZED, e);
        }
    }

}
