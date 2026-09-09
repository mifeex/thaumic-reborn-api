package com.thaumicreborn.api.knowledge;

import java.util.Map;
import java.util.Set;

/** Immutable synchronized player-knowledge state. */
public record KnowledgeSnapshot(Set<String> knownAspects, Map<String, Integer> aspectAmounts,
        Set<String> completedScans, Set<String> revealedResearch,
        Set<String> completedResearch, Set<String> researchCriteria,
        Map<WarpType, Integer> warp, int warpCounter, int runicCharge) {
    public KnowledgeSnapshot {
        knownAspects = Set.copyOf(knownAspects); aspectAmounts = Map.copyOf(aspectAmounts);
        completedScans = Set.copyOf(completedScans); revealedResearch = Set.copyOf(revealedResearch);
        completedResearch = Set.copyOf(completedResearch); researchCriteria = Set.copyOf(researchCriteria);
        warp = Map.copyOf(warp);
        if (warpCounter < 0 || runicCharge < 0) throw new IllegalArgumentException("state values cannot be negative");
    }
    public int nonTemporaryWarp() { return warp.getOrDefault(WarpType.PERMANENT, 0) + warp.getOrDefault(WarpType.NORMAL, 0); }
    public int totalWarp() { return nonTemporaryWarp() + warp.getOrDefault(WarpType.TEMPORARY, 0); }
}
