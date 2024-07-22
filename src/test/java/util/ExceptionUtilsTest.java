package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionUtilsTest {

    @Test
    void sneakyThrow() {
        Throwable t = new Throwable();
        assertThrows(Throwable.class, () -> ExceptionUtils.sneakyThrow(t));
    }
}