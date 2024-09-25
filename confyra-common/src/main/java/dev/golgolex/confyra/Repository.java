package dev.golgolex.confyra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public abstract class Repository<E extends Entity> {

    private final Layer<E> layer;
    private final RepositoryRunner<E> runner;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder<E extends Entity> {

        private Class<Layer<? extends Entity>> layer;
        private String id;

        public Builder(@NotNull Class<Layer<? extends Entity>> layer) {
            this.layer = layer;
        }

        public Builder withId(@NotNull String id) {
            this.id = id;
            return this;
        }

        public Repository<E> build() {
            return new
        }

    }

}
