package com.thaumicreborn.api.world;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import java.util.Optional;

/** Server-authoritative taint extension points. Register during common setup via enqueueWork. */
public interface TaintApi {
    /** Compatibility fallback for providers predating this API. */
    TaintApi UNSUPPORTED = new TaintApi() { };

    /** Register an existing Forge entity type as tainted; duplicate types are rejected. */
    default void registerMob(TaintMobDefinition definition) { throw unsupported(); }

    /** Override the Flux Taint death conversion for one exact entity type. Result must be registered tainted. */
    default void registerMobConversion(ResourceLocation original, ResourceLocation tainted) { throw unsupported(); }

    /**
     * Make an existing block a passive spread source and a taint neighbour for native ecology.
     * Loaded, ticking chunks sample sources at randomTickSpeed, even for blocks without randomTicks().
     * Register once during setup; duplicate or unknown block IDs are rejected.
     */
    default void registerSpreadSource(ResourceLocation block) { throw unsupported(); }

    /**
     * Custom rules precede native conversions, in ascending rule-ID order (independent of registration order).
     * Register once during setup; duplicate rule IDs are rejected. Callbacks must be side-effect-free.
     */
    default void registerBlockConversion(TaintBlockConversion conversion) { throw unsupported(); }

    default boolean isTainted(LivingEntity entity) { return false; }

    /** Apply Flux Taint; respects immunity and undead exclusion. */
    default boolean infect(LivingEntity entity, int durationTicks, int amplifier) { return false; }

    /**
     * Spawn a registered/native tainted mob at the centre of a loaded, unobstructed position.
     * Mob creation, spawn finalization and insertion are performed by the provider.
     */
    default Optional<Mob> spawn(ServerLevel level, BlockPos position, ResourceLocation entityType) {
        return Optional.empty();
    }

    /** Immediately convert a living non-player victim; discard it only after replacement insertion succeeds. */
    default Optional<Mob> convert(LivingEntity victim) { return Optional.empty(); }

    /** Apply a custom/native block conversion at one loaded position; no biome prerequisite. */
    default boolean infectBlock(ServerLevel level, BlockPos position) { return false; }

    /**
     * One bounded spread attempt around a loaded source: one neighbouring block and a chance of biome spread.
     * Can be called by addon blocks or mobs; the source itself need not be registered.
     * Does not remove/decay the source outside Tainted Lands.
     */
    default boolean spreadFrom(ServerLevel level, BlockPos source) { return false; }

    private static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("This provider does not support taint extensions");
    }
}
