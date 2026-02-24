package monolib.web.cookie;

import monolib.core.model.Session;

public interface CookieService {
    void setCookies(Session session);
    void clearCookies();
    void clearAccess();
}
