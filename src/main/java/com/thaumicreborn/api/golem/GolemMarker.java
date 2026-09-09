package com.thaumicreborn.api.golem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import java.util.Objects;
public record GolemMarker(BlockPos position, Direction side, int color) {
    public GolemMarker {
        position = Objects.requireNonNull(position, "position").immutable(); Objects.requireNonNull(side, "side");
        if (color < -1 || color > 15) throw new IllegalArgumentException("color must be -1..15");
    }
    @Override public BlockPos position() { return position.immutable(); }
}
