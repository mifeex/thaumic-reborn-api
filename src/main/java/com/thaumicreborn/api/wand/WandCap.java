package com.thaumicreborn.api.wand;

import java.util.List;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

public record WandCap(
        String id,
        float costModifier,
        String translationKey,
        String researchId,
        List<String> specialAspects,
        float specialCostModifier,
        ResourceLocation texture
) {
    public WandCap {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(translationKey, "translationKey");
        Objects.requireNonNull(researchId, "researchId");
        Objects.requireNonNull(texture, "texture");
        specialAspects = List.copyOf(specialAspects);
    }

    /**
     * Retains the 2.0.1 constructor and applies the main mod's conventional
     * texture path. Data-driven components should use the canonical
     * constructor so addon namespaces and custom paths are preserved.
     */
    public WandCap(
            String id,
            float costModifier,
            String translationKey,
            String researchId,
            List<String> specialAspects,
            float specialCostModifier
    ) {
        this(id, costModifier, translationKey, researchId, specialAspects,
                specialCostModifier, defaultTexture(id));
    }

    private static ResourceLocation defaultTexture(String id) {
        return ResourceLocation.fromNamespaceAndPath(
                "thaumic_reborn",
                "textures/item/wand_cap_" + id + "_model.png"
        );
    }
}
