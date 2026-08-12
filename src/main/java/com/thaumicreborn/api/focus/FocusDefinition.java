package com.thaumicreborn.api.focus;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Objects;

/** Player-facing and casting metadata for a wand focus. Costs use centi-vis. */
public record FocusDefinition(ResourceLocation id, int color, boolean continuous,
                              int cooldownTicks, Map<String, Integer> centivisCost) {
    public FocusDefinition {
        Objects.requireNonNull(id, "id");
        if (cooldownTicks < 0) throw new IllegalArgumentException("cooldownTicks must be >= 0");
        centivisCost = Map.copyOf(centivisCost);
        centivisCost.forEach((aspect, amount) -> {
            if (aspect == null || aspect.isBlank() || amount < 0)
                throw new IllegalArgumentException("invalid focus cost");
        });
    }
}
