package dev.golgolex.confyra;

import dev.golgolex.quala.common.data.Trio;
import dev.golgolex.quala.common.utils.supply.ElementSupply;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class LayerService {

    private static final Map<Class<? extends Layer<? extends Entity>>, Layer<? extends Entity>> layers = new HashMap<>();
    private static final Map<Class<? extends Layer<? extends Entity>>,
            ElementSupply<? extends Layer<? extends Entity>, Trio<String, String, Object[]>>> customContractors = new HashMap<>();

    public static <E extends Entity, T extends Layer<E>> @NotNull T contract(@NotNull Class<T> layer,
                                                                             @Nullable String id,
                                                                             @Nullable String prefix,
                                                                             @Nullable Object[] object) {
        if (customContractors.containsKey(layer)) {
            return (T) customContractors.get(layer).construct(new Trio<>(id, prefix, object));
        }
        try {
            return layer.getConstructor(String.class, String.class).newInstance(
                    id,
                    prefix
            );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
