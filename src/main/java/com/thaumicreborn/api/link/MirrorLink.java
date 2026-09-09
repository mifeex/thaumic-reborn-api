package com.thaumicreborn.api.link;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import java.util.Objects;
public record MirrorLink(ResourceLocation dimension, BlockPos position) {
    public MirrorLink { Objects.requireNonNull(dimension, "dimension"); position = Objects.requireNonNull(position, "position").immutable(); }
    @Override public BlockPos position() { return position.immutable(); }
}
