package dev.golgolex.confyra.config;

/*
 * Copyright 2023-2024 confyra contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import dev.golgolex.confyra.Language;
import dev.golgolex.confyra.ReplaceSupply;
import dev.golgolex.confyra.Repository;
import dev.golgolex.quala.common.json.JsonDocument;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;

public class ConfigRepository extends Repository {

    protected final Path directoryPath;
    protected Path filePath;
    protected JsonDocument configuration;

    protected ConfigRepository(@NotNull List<ReplaceSupply> replaceSupplies,
                               @NotNull String prefix,
                               @NotNull Path directoryPath) {
        super(replaceSupplies, prefix);
        this.directoryPath = directoryPath.resolve(prefix);
    }

    @Override
    public void init(@NotNull Language owner) {
        if (!this.directoryPath.toFile().exists()) {
            var ignore = this.directoryPath.toFile().mkdirs();
        }
        this.filePath = directoryPath.resolve(owner.name() + ".json");
        var file = this.filePath.toFile();
        if (!file.exists()) {
            new JsonDocument()
                    .write("locale-tag", owner.tag())
                    .write("locale-name", owner.name())
                    .saveAsConfig(filePath);
        }
        this.configuration = JsonDocument.fromPath(filePath);
        this.loadEntries();
    }

    @Override
    public void loadEntries() {
        this.entries().clear();
        for (var jsonElementEntry : this.configuration.jsonObject().entrySet()) {
            this.entries().add(new Entry(jsonElementEntry.getKey(), jsonElementEntry.getValue().getAsString()));
        }
    }

    @Override
    public String getEntry(@NotNull String key) {
        return this.entries()
                .stream()
                .filter(entry -> entry.key().equalsIgnoreCase(key))
                .findFirst()
                .map(Entry::value)
                .orElse(null);
    }

    @Override
    public void addEntry(@NotNull String key, @NotNull String value) {
        if (this.configuration.contains(key)) return;
        this.setEntry(key, value);
    }

    @Override
    public void setEntry(@NotNull String key, @NotNull String value) {
        this.configuration.delete(key);
        this.configuration.write(key, value);
        this.configuration.saveAsConfig(directoryPath);
    }

}
