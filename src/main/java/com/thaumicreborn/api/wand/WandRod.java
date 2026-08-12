package com.thaumicreborn.api.wand;

import java.util.List;
import java.util.Objects;

public record WandRod(
        String id,
        int capacityVis,
        String translationKey,
        String researchId,
        List<String> rechargeAspects,
        boolean staff,
        boolean runes
) {
    public WandRod {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(translationKey, "translationKey");
        Objects.requireNonNull(researchId, "researchId");
        rechargeAspects = List.copyOf(rechargeAspects);
    }
}
