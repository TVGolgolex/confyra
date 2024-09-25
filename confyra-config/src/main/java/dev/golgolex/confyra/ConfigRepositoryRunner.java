package dev.golgolex.confyra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ConfigRepositoryRunner extends RepositoryRunner<Entity> {

    private final Map<String, Map<String, String>> content;
    private final Path filePath;

    public ConfigRepositoryRunner(Layer<Entity> layer, Path filePath) {
        super(layer);
        this.filePath = filePath;
        this.content = new HashMap<>();
    }

    public void load() throws IOException {
        try (var br = new BufferedReader(new FileReader(filePath.toFile()))) {
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
    public List<Entity> parse() {
        try {
            this.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.content.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(this.layer().prefix()))
                .flatMap(entry -> entry.getValue().entrySet().stream()
                        .map(e -> new Entity(e.getKey(), e.getValue())))
                .toList();
    }

    @Override
    public void write(Entity entity) {

    }
}
