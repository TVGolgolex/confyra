package dev.golgolex.confyra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public abstract class Layer<E extends Entity> {

    private final String id;
    private final String prefix;

}
