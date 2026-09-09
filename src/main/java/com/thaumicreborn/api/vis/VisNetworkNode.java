package com.thaumicreborn.api.vis;

import net.minecraft.core.BlockPos;
import java.util.Map;
import java.util.Objects;

/** Immutable route-aware state for a relay, charger or energized source. */
public record VisNetworkNode(BlockPos position, NodeKind kind, int attunement,
        BlockPos parent, BlockPos source, Map<String, Integer> availableVis) {
    public VisNetworkNode {
        position = Objects.requireNonNull(position, "position").immutable();
        parent = parent == null ? null : parent.immutable(); source = source == null ? null : source.immutable();
        Objects.requireNonNull(kind, "kind"); availableVis = Map.copyOf(availableVis);
        if (attunement < -1 || attunement > 5) throw new IllegalArgumentException("attunement must be -1..5");
    }
    public enum NodeKind { SOURCE, RELAY, CHARGER, CHANNEL, DEVICE }
}
