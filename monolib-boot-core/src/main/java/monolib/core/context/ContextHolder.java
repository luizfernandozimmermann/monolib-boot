package monolib.core.context;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ContextHolder {

    private static final ThreadLocal<Context> holder = new ThreadLocal<>();

    public static void set(Context context) {
        holder.set(context);
    }

    public static Context get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }

}
