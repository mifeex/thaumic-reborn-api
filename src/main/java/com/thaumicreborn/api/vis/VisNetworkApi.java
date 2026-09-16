package com.thaumicreborn.api.vis;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import java.util.Optional;

/** Narrow server-side view of loaded vis-network topology and transfers. */
public interface VisNetworkApi {
    /** Register a loaded VisSource block entity. Re-register after chunk load. */
    default void registerSource(ServerLevel level, BlockPos position) { }

    Optional<VisNetworkNode> node(ServerLevel level, BlockPos position);
    /** Available supply after intersecting every aspect filter on the selected route. */
    int available(ServerLevel level, BlockPos position, String primalAspect);
    /** Rejected aspects consume nothing and produce no transfer pulse. */
    int consume(ServerLevel level, BlockPos position, String primalAspect, int amount);
    int consumeNearest(ServerLevel level, BlockPos origin, String primalAspect, int amount);
    boolean setAttunement(ServerLevel level, BlockPos position, int attunement);
}
