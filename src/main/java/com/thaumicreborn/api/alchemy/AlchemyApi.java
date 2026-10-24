package com.thaumicreborn.api.alchemy;
import net.minecraft.world.item.ItemStack;
import java.util.Map;
/** Item decomposition and mutable essentia-store discovery. */
public interface AlchemyApi {
    Map<String, Integer> itemAspects(ItemStack stack);
    /** Native cluster for a supported ore, or empty. */
    default ItemStack nativeCluster(ItemStack ore) { return ItemStack.EMPTY; }
    /** Native Infernal Furnace bonus item and base count, or empty. */
    default ItemStack smeltingBonus(ItemStack source) { return ItemStack.EMPTY; }
}
