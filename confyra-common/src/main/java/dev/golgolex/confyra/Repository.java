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

import dev.golgolex.quala.netty5.basic.protocol.buffer.BufferClass;
import dev.golgolex.quala.netty5.basic.protocol.buffer.CodecBuffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Repository implements BufferClass {

    private List<Entry> entries = new ArrayList<>();
    private List<ReplaceSupply> replaceSupplies;
    private String prefix;

    protected Repository(@NotNull List<ReplaceSupply> replaceSupplies,
                         @NotNull String prefix) {
        this.replaceSupplies = replaceSupplies;
        this.prefix = prefix;
    }

    @Override
    public void writeBuffer(@NotNull CodecBuffer codecBuffer) {
        codecBuffer.writeString(prefix)
                .writeList(entries, CodecBuffer::writeBufferClass);
    }

    @Override
    public void readBuffer(@NotNull CodecBuffer codecBuffer) {
        this.prefix = codecBuffer.readString();
        this.entries = codecBuffer.readList(new ArrayList<>(), () -> codecBuffer.readBufferClass(new Entry()));
    }

    public abstract void init(@NotNull Language owner);

    public abstract void loadEntries();

    public abstract String getEntry(@NotNull String key);

    public String getReplacedEntry(@NotNull String key) {
        var value = this.getEntry(key);
        if (value == null) return key;

        var tmp = value;
        for (var replaceSupply : this.replaceSupplies) {
            tmp = replaceSupply.replace().construct(tmp);
        }
        return tmp;
    }

    public abstract void addEntry(@NotNull String key, @NotNull String value);

    public abstract void setEntry(@NotNull String key, @NotNull String value);

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Entry implements BufferClass {
        private String key;
        private String value;

        @Override
        public void writeBuffer(@NotNull CodecBuffer codecBuffer) {
            codecBuffer.writeString(this.key)
                    .writeString(this.value);
        }

        @Override
        public void readBuffer(@NotNull CodecBuffer codecBuffer) {
            this.key = codecBuffer.readString();
            this.value = codecBuffer.readString();
        }
    }

}