package running.tracker.unit.util;

import java.util.Random;
import java.util.UUID;

public class RandomValueGenerator {

    public static String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    public static Double generateRandomDouble() {
        return new Random().nextDouble();
    }
}