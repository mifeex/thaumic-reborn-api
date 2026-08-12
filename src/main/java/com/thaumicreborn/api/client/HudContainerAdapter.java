package com.thaumicreborn.api.client;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

@FunctionalInterface
public interface HudContainerAdapter<T extends BlockEntity> {
    Optional<HudReadout> resolve(T blockEntity, BlockHitResult hit);
}
