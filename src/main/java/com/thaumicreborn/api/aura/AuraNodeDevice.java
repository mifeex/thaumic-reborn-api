package com.thaumicreborn.api.aura;
import net.minecraft.core.BlockPos;
/** Loaded block-entity influences, evaluated live: never permanently mutate node type. */
public interface AuraNodeDevice {
    default boolean suppressesHunger(BlockPos node) { return false; }
    /** Additional raw node vis before energized square-root conversion; max 10 per device. */
    default int transductionBoost(BlockPos node) { return 0; }
}
