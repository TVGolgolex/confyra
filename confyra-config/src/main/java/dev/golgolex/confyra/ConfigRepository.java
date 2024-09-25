package dev.golgolex.confyra;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ConfigRepository implements Repository {

    private final Layer layer;
    private Map<String, Map<String, String>> content;
    private Path path;

    public ConfigRepository(@NotNull Layer layer) {
        this.layer = layer;
        this.content = new ConcurrentHashMap<>();
    }

    public void loadConfig() throws IOException {
        try (var br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("$_")) {
                    var parts = line.split("=", 2);
                    if (parts.length == 2) {
                        var prefix = StringExtractor.extractPrefix(parts[0].trim());
                        var key = StringExtractor.extractKey(parts[0].trim());
                        var value = StringExtractor.extractValue(parts[1].trim());
                        this.content.computeIfAbsent(prefix, _ -> new HashMap<>());
                        this.content.get(prefix).put(key, value);
                    }
                }
            }
        }
    }

    @Override
    public Layer layer() {
        return layer;
    }

    public List<Entity> collect() {
        return this.content.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(this.layer.prefix()))
                .flatMap(entry -> entry.getValue().entrySet().stream()
                        .map(e -> new Entity(e.getKey(), e.getValue())))
                .toList();
    }

    @Override
    public void write(List<Entity> entities) {

    }

}
