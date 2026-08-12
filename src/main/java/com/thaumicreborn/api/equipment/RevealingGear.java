package com.thaumicreborn.api.equipment;

import net.minecraft.world.item.ItemStack;

/** Equipment that enables Thaumic Reborn's node and container HUD. */
public interface RevealingGear {
    boolean reveals(ItemStack stack);
}
