package dev.golgolex.confyra;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class StringExtractor {

    public String extractPrefix(@NotNull String part) {
        return part.substring(part.indexOf('_') + 1, part.indexOf('['));
    }

    public String extractKey(@NotNull String keyPart) {
        return keyPart.substring(keyPart.indexOf('[') + 2, keyPart.indexOf(']') - 1);
    }

    public String extractValue(@NotNull String valuePart) {
        return valuePart.substring(1, valuePart.length() - 2);
    }
}
