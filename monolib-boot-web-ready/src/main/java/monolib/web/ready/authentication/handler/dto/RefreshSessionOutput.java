package monolib.web.ready.authentication.handler.dto;

import lombok.Getter;
import lombok.Setter;
import monolib.core.model.Session;

@Getter
@Setter
public class RefreshSessionOutput {

    private SessionInfo session;

    public RefreshSessionOutput(Session session) {
        this.session = new SessionInfo(session);
    }

}
