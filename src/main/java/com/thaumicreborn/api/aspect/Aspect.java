package com.thaumicreborn.api.aspect;

import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Objects;

/** Immutable public description of an aspect. */
public record Aspect(
        String id,
        int color,
        ResourceLocation icon,
        List<String> components,
        int order
) {
    public Aspect {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(icon, "icon");
        components = List.copyOf(components);
        if (id.isBlank()) throw new IllegalArgumentException("id cannot be blank");
        if (color < 0 || color > 0xFFFFFF) {
            throw new IllegalArgumentException("color must be a 24-bit RGB value");
        }
        if (!components.isEmpty() && components.size() != 2) {
            throw new IllegalArgumentException("an aspect has zero or two components");
        }
    }

    public boolean primal() {
        return components.isEmpty();
    }
}
