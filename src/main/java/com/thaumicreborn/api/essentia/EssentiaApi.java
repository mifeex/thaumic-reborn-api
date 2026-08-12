package com.thaumicreborn.api.essentia;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import java.util.Optional;

/** Queries the live essentia network without exposing implementation classes. */
public interface EssentiaApi {
    Optional<EssentiaTransport> transport(Level level, BlockPos position);
    Optional<EssentiaTransport> neighbour(Level level, BlockPos position, Direction side);
    boolean connected(Level level, BlockPos position, Direction side, EssentiaTransport local);
}
