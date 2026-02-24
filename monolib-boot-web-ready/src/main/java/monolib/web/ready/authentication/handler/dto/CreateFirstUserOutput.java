package monolib.web.ready.authentication.handler.dto;

import lombok.Getter;
import lombok.Setter;
import monolib.core.model.Session;

@Getter
@Setter
public class CreateFirstUserOutput {

    private SessionInfo session;

    public CreateFirstUserOutput(Session session) {
        this.session = new SessionInfo(session);
    }
}
