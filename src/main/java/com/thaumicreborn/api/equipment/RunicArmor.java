package com.thaumicreborn.api.equipment;

import net.minecraft.world.item.ItemStack;

/** Equipment that contributes to Thaumic Reborn's runic shield. */
public interface RunicArmor {
    int baseRunicCharge(ItemStack stack);
}
