package com.thaumicreborn.api.focus;

import net.minecraft.resources.ResourceLocation;
import java.util.List;
import java.util.Map;

/** Immutable five-rank upgrade state of one focus stack. */
public record FocusState(ResourceLocation focusId, int nextRank,
        List<ResourceLocation> upgradesByRank, Map<ResourceLocation, Integer> levels) {
    public FocusState {
        upgradesByRank = List.copyOf(upgradesByRank); levels = Map.copyOf(levels);
    }
    public boolean complete() { return nextRank < 0; }
}
