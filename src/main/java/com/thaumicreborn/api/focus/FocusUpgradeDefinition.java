package com.thaumicreborn.api.focus;

import net.minecraft.resources.ResourceLocation;
import java.util.Map;
import java.util.Objects;

/** Stable identity, display metadata and compound-aspect base cost of an upgrade. */
public record FocusUpgradeDefinition(ResourceLocation id, int legacyId, ResourceLocation icon,
        String nameKey, String descriptionKey, Map<String, Integer> aspectCost) {
    public FocusUpgradeDefinition {
        Objects.requireNonNull(id, "id"); Objects.requireNonNull(icon, "icon");
        Objects.requireNonNull(nameKey, "nameKey"); Objects.requireNonNull(descriptionKey, "descriptionKey");
        aspectCost = Map.copyOf(aspectCost);
    }
}
