package monolib.core.context;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import monolib.core.model.Session;
import monolib.core.model.User;

import java.util.Locale;
import java.util.Optional;

@Getter
@SuperBuilder
@NoArgsConstructor
@Setter(AccessLevel.PROTECTED)
public abstract class Context {

    protected Session session;
    protected Locale locale;

    protected abstract Session provideSession();

    protected abstract Locale provideLocale();

    public User getUser() {
        return Optional.ofNullable(getSession())
                .map(Session::getUser)
                .orElse(null);
    }

    public String getEmail() {
        return Optional.ofNullable(getUser())
                .map(User::getEmail)
                .orElse(null);
    }

    public void register() {
        register(this);
    }

    protected void register(Context context) {
        ContextHolder.set(context);
        context.session = provideSession();
        context.locale = provideLocale();
    }

}
