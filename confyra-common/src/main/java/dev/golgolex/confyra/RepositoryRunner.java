package dev.golgolex.confyra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public abstract class RepositoryRunner<E extends Entity> {

    private final Layer<E> layer;

    public abstract List<E> parse();

    public abstract void write(E entity);

}
