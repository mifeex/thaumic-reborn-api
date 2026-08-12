package com.thaumicreborn.api.aspect;

import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Read-only access to the active, datapack-reloadable aspect registry. */
public interface AspectApi {
    Optional<Aspect> find(String id);

    List<Aspect> all();

    Map<String, Integer> aspects(ItemStack stack);
}
