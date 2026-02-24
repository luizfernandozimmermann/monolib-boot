package monolib.data.domain.usersession.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.domain.usersession.model.QUserSessionEntity;
import monolib.data.domain.usersession.repository.UserSessionCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSessionCustomRepositoryImpl implements UserSessionCustomRepository {

    private static final QUserSessionEntity qSession = QUserSessionEntity.userSessionEntity;

    JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public void revokeSession(UUID id, LocalDateTime dateTime) {
        queryFactory.update(qSession)
                .set(qSession.revokedAt, dateTime)
                .where(qSession.id.eq(id).and(qSession.revokedAt.isNull()))
                .execute();
    }

    @Override
    @Transactional
    public void revokeAllSessionsByUser(UUID userId) {
        var revokedAt = LocalDateTime.now();
        queryFactory.update(qSession)
                .set(qSession.revokedAt, revokedAt)
                .where(qSession.user.id.eq(userId).and(qSession.revokedAt.isNull()))
                .execute();
    }

}
