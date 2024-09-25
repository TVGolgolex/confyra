package dev.golgolex.confyra;

import org.jetbrains.annotations.NotNull;

public record Entity(@NotNull String key,
                     @NotNull String value) {
}
