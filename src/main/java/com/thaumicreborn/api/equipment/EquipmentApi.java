package com.thaumicreborn.api.equipment;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** Shared equipment integration queries used by addons and the main mod. */
public interface EquipmentApi {
    int visDiscountPercent(ItemStack stack, Player player, String primalAspect);
    boolean reveals(ItemStack stack);
    int runicCharge(ItemStack stack);
    boolean repairable(ItemStack stack);
}
