package org.lcy.utils;

public class IdUtils {


    public static String generateId() {
        return java.util.UUID.randomUUID()
                .toString()
                .replace("-", "");
    }
}
