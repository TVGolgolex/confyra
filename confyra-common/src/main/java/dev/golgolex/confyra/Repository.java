package dev.golgolex.confyra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public abstract class Repository<E extends Entity> {

    private final Layer layer;
    private final List<Entity> collect;
    private final RepositoryRunner<E> runner;

}
