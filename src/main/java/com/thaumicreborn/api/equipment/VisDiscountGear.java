package com.thaumicreborn.api.equipment;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** Aspect-specific vis discount in signed percentage points. */
public interface VisDiscountGear {
    int visDiscountPercent(ItemStack stack, Player player, String primalAspect);
}
