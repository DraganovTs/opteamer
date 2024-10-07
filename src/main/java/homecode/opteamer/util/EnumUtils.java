package homecode.opteamer.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EnumUtils {
    public static <E extends Enum<E>> String getEnumValues(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants()) // Get enum constants
                .map(Enum::name) // Convert to their string representation
                .collect(Collectors.joining(", ")); // Join into a single string
    }
}
