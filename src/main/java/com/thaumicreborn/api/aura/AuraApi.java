package com.thaumicreborn.api.aura;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.function.Predicate;

/** Read and server-authoritative mutation access for Thaumic Reborn aura nodes. */
public interface AuraApi {
    Optional<AuraNode> node(Level level, BlockPos position);
    List<LocatedAuraNode> withinCube(ServerLevel level, BlockPos origin, int radius);
    Optional<LocatedAuraNode> nearest(ServerLevel level, BlockPos origin, int radius,
                                     Predicate<AuraNode> filter);
    boolean initialize(ServerLevel level, BlockPos position, AuraNode node);
    boolean replaceAspects(ServerLevel level, BlockPos position, long expectedRevision,
                           Map<String, Integer> current, Map<String, Integer> maximum);
    int drain(ServerLevel level, BlockPos position, String primalAspect, int maximum);
    boolean remove(ServerLevel level, BlockPos position, long expectedRevision);
}
