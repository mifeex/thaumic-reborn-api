package com.thaumicreborn.api.aura;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** Immutable, synchronized snapshot of a world aura node. */
public record AuraNode(UUID id, AuraNodeType type, AuraNodeModifier modifier,
                       Map<String, Integer> current, Map<String, Integer> maximum,
                       long revision) {
    public AuraNode {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(modifier, "modifier");
        current = Map.copyOf(current);
        maximum = Map.copyOf(maximum);
    }
}
