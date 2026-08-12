package com.thaumicreborn.api.scan;

import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Objects;

public record Scan(
        ScanTargetType type,
        ResourceLocation targetId,
        String displayKey,
        List<AspectReward> rewards,
        String knowledgeKey
) {
    public Scan {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(targetId, "targetId");
        displayKey = displayKey == null ? "" : displayKey;
        rewards = List.copyOf(rewards);
        Objects.requireNonNull(knowledgeKey, "knowledgeKey");
    }
}
