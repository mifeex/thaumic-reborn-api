package com.thaumicreborn.api.wand;

import java.util.List;
import java.util.Objects;

public record WandCap(
        String id,
        float costModifier,
        String translationKey,
        String researchId,
        List<String> specialAspects,
        float specialCostModifier
) {
    public WandCap {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(translationKey, "translationKey");
        Objects.requireNonNull(researchId, "researchId");
        specialAspects = List.copyOf(specialAspects);
    }
}
