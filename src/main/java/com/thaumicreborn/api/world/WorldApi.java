package com.thaumicreborn.api.world;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
/** Queries and bounded transformations for public thaumic world state. */
public interface WorldApi {
    ResourceLocation magicalForestBiome();
    ResourceLocation taintedLandsBiome();
    ResourceLocation outerLandsDimension();
    boolean isMagicalForest(ServerLevel level, BlockPos position);
    boolean isTaintedLands(ServerLevel level, BlockPos position);
    boolean isOuterLands(ServerLevel level);
    boolean convertToTaintedLands(ServerLevel level, BlockPos position);
    boolean restoreGeneratedBiome(ServerLevel level, BlockPos position);
}
