package monolib.data.domain.usersession.converter;

import monolib.core.model.Session;
import monolib.data.domain.usersession.model.UserSessionEntity;

public interface UserSessionConverter {
    Session convert(UserSessionEntity entity);
    UserSessionEntity convert(UserSessionEntity entity, Session session);
}
