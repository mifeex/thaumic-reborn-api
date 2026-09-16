package com.thaumicreborn.api.aspect;

import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Access to the active aspect registry and typed essence item payloads. */
public interface AspectApi {
    Optional<Aspect> find(String id);

    List<Aspect> all();

    Map<String, Integer> aspects(ItemStack stack);

    /** Stored payload only, excluding the item's inherent Auram scan aspects. */
    default Optional<String> etherealEssenceAspect(ItemStack stack) { return Optional.empty(); }

    /** A single essence item with a validated stored aspect amount; invalid input returns empty. */
    default ItemStack createEtherealEssence(String aspect, int amount) { return ItemStack.EMPTY; }

}
