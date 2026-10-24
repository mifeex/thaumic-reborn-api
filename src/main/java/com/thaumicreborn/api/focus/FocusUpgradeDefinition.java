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
        if (legacyId < -1) throw new IllegalArgumentException("legacyId must be -1 for custom upgrades");
        if (nameKey.isBlank() || descriptionKey.isBlank()) throw new IllegalArgumentException("blank upgrade translation key");
        aspectCost = Map.copyOf(aspectCost);
        aspectCost.forEach((aspect,amount)->{
            if(aspect.isBlank() || amount<=0) throw new IllegalArgumentException("invalid upgrade aspect cost");
        });
    }
}
