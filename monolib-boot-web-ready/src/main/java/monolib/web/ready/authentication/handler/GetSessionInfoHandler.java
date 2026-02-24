package monolib.web.ready.authentication.handler;

import monolib.core.context.ContextHolder;
import monolib.web.annotation.GetRequest;
import monolib.web.annotation.Handler;
import monolib.web.ready.authentication.handler.dto.GetSessionInfoOutput;

@Handler(path = "authentication")
public class GetSessionInfoHandler {

    @GetRequest(path = "/getSessionInfo", authenticateFirstSession = false)
    public GetSessionInfoOutput getSessionInfo() {
        return new GetSessionInfoOutput(ContextHolder.get().getSession());
    }

}
