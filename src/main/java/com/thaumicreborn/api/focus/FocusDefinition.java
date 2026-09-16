package com.thaumicreborn.api.focus;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.List;
import java.util.Objects;

/** Player-facing and casting metadata for a wand focus. Costs use centi-vis. */
public record FocusDefinition(ResourceLocation id, int color, boolean continuous,
                              int cooldownTicks, Map<String, Integer> centivisCost,
                              boolean perTickCost, int maximumRanks,
                              Map<Integer, List<ResourceLocation>> upgradesByRank,
                              FocusAnimation animation) {
    public FocusDefinition(ResourceLocation id, int color, boolean continuous,
            int cooldownTicks, Map<String, Integer> centivisCost) {
        this(id, color, continuous, cooldownTicks, centivisCost, continuous,
                5, Map.of(), FocusAnimation.WAVE);
    }
    /** Short constructor for addons that only need to select the casting pose. */
    public FocusDefinition(ResourceLocation id, int color, boolean continuous,
            int cooldownTicks, Map<String, Integer> centivisCost,
            FocusAnimation animation) {
        this(id, color, continuous, cooldownTicks, centivisCost, continuous,
                5, Map.of(), animation);
    }
    /** Retains the pre-animation API constructor with the TC4 WAVE default. */
    public FocusDefinition(ResourceLocation id, int color, boolean continuous,
            int cooldownTicks, Map<String, Integer> centivisCost,
            boolean perTickCost, int maximumRanks,
            Map<Integer, List<ResourceLocation>> upgradesByRank) {
        this(id, color, continuous, cooldownTicks, centivisCost, perTickCost,
                maximumRanks, upgradesByRank, FocusAnimation.WAVE);
    }
    public FocusDefinition {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(animation, "animation");
        if (cooldownTicks < 0) throw new IllegalArgumentException("cooldownTicks must be >= 0");
        centivisCost = Map.copyOf(centivisCost);
        centivisCost.forEach((aspect, amount) -> {
            if (aspect == null || aspect.isBlank() || amount < 0)
                throw new IllegalArgumentException("invalid focus cost");
        });
        if (maximumRanks < 0) throw new IllegalArgumentException("maximumRanks must be >= 0");
        java.util.LinkedHashMap<Integer, List<ResourceLocation>> copied = new java.util.LinkedHashMap<>();
        upgradesByRank.forEach((rank, upgrades) -> {
            if (rank == null || rank < 1 || rank > maximumRanks) throw new IllegalArgumentException("invalid focus rank");
            copied.put(rank, List.copyOf(upgrades));
        });
        upgradesByRank = Map.copyOf(copied);
    }
}
