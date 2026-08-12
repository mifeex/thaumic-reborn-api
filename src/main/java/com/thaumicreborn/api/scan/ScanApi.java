package com.thaumicreborn.api.scan;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

/** Queries the active scan definitions; registration is datapack-driven. */
public interface ScanApi {
    Optional<Scan> find(ScanTargetType type, ResourceLocation targetId);

    Optional<Scan> findForItem(ItemStack stack);

    List<Scan> all();
}
