package com.thaumicreborn.api.aura;

import net.minecraft.core.BlockPos;
import java.util.Objects;

/** An immutable node snapshot paired with its loaded world position. */
public record LocatedAuraNode(BlockPos position, AuraNode node) {
    public LocatedAuraNode {
        position = Objects.requireNonNull(position, "position").immutable();
        Objects.requireNonNull(node, "node");
    }
    @Override public BlockPos position() { return position.immutable(); }
}
