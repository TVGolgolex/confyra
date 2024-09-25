package dev.golgolex.confyra;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.nio.file.Path;
import java.util.List;

@Getter
@Accessors(fluent = true)
public final class ConfigRepository extends Repository<Entity> {

    private final Layer<Entity> layer;
    private final Path path;

    public ConfigRepository(Layer<Entity> layer, Path path) {
        super(layer, new ConfigRepositoryRunner(layer, path));
        this.layer = layer;
        this.path = path;
    }

    @Override
    public Layer<Entity> layer() {
        return layer;
    }
}
