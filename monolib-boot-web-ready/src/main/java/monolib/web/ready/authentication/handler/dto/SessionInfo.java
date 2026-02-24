package monolib.web.ready.authentication.handler.dto;

import lombok.Getter;
import lombok.Setter;
import monolib.core.model.Session;

import java.time.LocalDateTime;

@Getter
@Setter
public class SessionInfo {

    private UserInfo user;
    private LocalDateTime expiresAt;
    private boolean firstSession;

    public SessionInfo(Session session) {
        user = new UserInfo(session.getUser());
        expiresAt = session.getExpiresAt();
        firstSession = session.isFirstSession();
    }
}
