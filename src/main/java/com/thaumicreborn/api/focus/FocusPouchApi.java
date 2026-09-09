package com.thaumicreborn.api.focus;

import net.minecraft.world.item.ItemStack;
import java.util.List;

/** Safe access to the current 18-slot focus-pouch state. */
public interface FocusPouchApi {
    int slotCount();
    boolean isPouch(ItemStack stack);
    List<ItemStack> contents(ItemStack pouch);
    boolean replaceContents(ItemStack pouch, List<ItemStack> contents);
}
