package util;



public class ExceptionUtils {
    private ExceptionUtils() {
        throw new IllegalStateException("Utility class");
    }
    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void sneakyThrowInternal(Throwable t) throws T {
        throw (T) t;
    }

    public static void sneakyThrow(Throwable t) {
        ExceptionUtils.sneakyThrowInternal(t);
    }
}
