package com.thaumicreborn.api.wand;

import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

/** Creation and inspection of data-driven casting tools. */
public interface WandApi {
    List<WandRod> rods();

    List<WandCap> caps();

    ItemStack createWand(String rodId, String capId, boolean filled);

    ItemStack createSceptre(String rodId, String capId, boolean filled);

    ItemStack createStaff(String rodId, String capId, boolean filled);

    Optional<WandState> state(ItemStack stack);

    boolean isCraftingTool(ItemStack stack);

    boolean acceptsFocus(ItemStack stack);
}
