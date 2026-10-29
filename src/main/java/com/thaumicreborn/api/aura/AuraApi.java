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
    /**
     * Create a native world node in an air block in an already loaded chunk.
     * Call on the server thread. Existing blocks/nodes are never overwritten and
     * chunks are never loaded by this operation. The provider saves and syncs the node.
     *
     * @return false when placement is unavailable (including older providers)
     * @throws IllegalArgumentException if the node state is invalid
     */
    default boolean create(ServerLevel level, BlockPos position, AuraNode node) {
        return false;
    }

    /** Create a fully charged node with a fresh UUID and revision zero. */
    default boolean create(ServerLevel level, BlockPos position, AuraNodeType type,
                           AuraNodeModifier modifier, Map<String, Integer> aspects) {
        return create(level, position, new AuraNode(java.util.UUID.randomUUID(),
                type, modifier, aspects, aspects, 0));
    }

    /** Register a loaded AuraNodeDevice block entity. Re-register after chunk load. */
    default void registerDevice(ServerLevel level, BlockPos position) { }

    Optional<AuraNode> node(Level level, BlockPos position);
    List<LocatedAuraNode> withinCube(ServerLevel level, BlockPos origin, int radius);
    Optional<LocatedAuraNode> nearest(ServerLevel level, BlockPos origin, int radius,
                                     Predicate<AuraNode> filter);
    /** Initialize an existing, uninitialized native node block; does not place a block. */
    boolean initialize(ServerLevel level, BlockPos position, AuraNode node);
    boolean replaceAspects(ServerLevel level, BlockPos position, long expectedRevision,
                           Map<String, Integer> current, Map<String, Integer> maximum);
    int drain(ServerLevel level, BlockPos position, String primalAspect, int maximum);
    boolean remove(ServerLevel level, BlockPos position, long expectedRevision);
}
