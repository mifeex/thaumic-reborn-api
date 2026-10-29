package com.thaumicreborn.api.world;

import net.minecraft.resources.ResourceLocation;
import java.util.Objects;

/**
 * Behaviour for an existing Forge mob type. Registered mobs reject taint/warp effects and reconversion.
 * A zero terrain interval disables automatic soil infection; zero infection duration disables attack infection.
 * Terrain actions respect mobGriefing; spreadBiome additionally taints the biome column under the mob.
 */
public record TaintMobDefinition(ResourceLocation entityType, int terrainIntervalTicks,
                                 boolean spreadBiome, int attackInfectionDurationTicks) {
    public TaintMobDefinition {
        Objects.requireNonNull(entityType, "entityType");
        if (terrainIntervalTicks < 0 || attackInfectionDurationTicks < 0) {
            throw new IllegalArgumentException("taint intervals/durations cannot be negative");
        }
        if (spreadBiome && terrainIntervalTicks == 0) {
            throw new IllegalArgumentException("biome spread requires a terrain interval");
        }
    }
}
