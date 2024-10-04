package dev.golgolex.confyra;

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

import dev.golgolex.quala.common.json.JsonDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Language {

    private String name;
    private String tag;
    private List<Repository> repositories;
    private JsonDocument properties;

    @SuppressWarnings("unchecked")
    public <T extends Repository> T getRepository(@NotNull String prefix) {
        try {
            return (T) this.repositories.stream().filter(repository -> repository.prefix().equalsIgnoreCase(prefix))
                    .findFirst().orElse(null);
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public <T extends Repository> T loadRepository(@NotNull T repository) {
        repository.init(this);
        this.repositories.add(repository);
        return repository;
    }

}