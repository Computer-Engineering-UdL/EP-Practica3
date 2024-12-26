package utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class NumberUtils {
    /**
     * Generates a random UUID that we can asume that will be unique
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static float generateRandomLatitude() {
        return (float) ThreadLocalRandom.current().nextDouble(-90, 90);
    }

    public static float generateRandomLongitude() {
        return (float) ThreadLocalRandom.current().nextDouble(-180, 180);
    }
}
