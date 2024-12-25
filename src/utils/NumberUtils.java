package utils;

import java.util.UUID;

public final class NumberUtils {
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
