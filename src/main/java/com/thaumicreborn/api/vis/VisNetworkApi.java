package com.thaumicreborn.api.vis;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import java.util.Optional;

/** Narrow server-side view of loaded vis-network topology and transfers. */
public interface VisNetworkApi {
    Optional<VisNetworkNode> node(ServerLevel level, BlockPos position);
    int available(ServerLevel level, BlockPos position, String primalAspect);
    int consume(ServerLevel level, BlockPos position, String primalAspect, int amount);
    int consumeNearest(ServerLevel level, BlockPos origin, String primalAspect, int amount);
    boolean setAttunement(ServerLevel level, BlockPos position, int attunement);
}
