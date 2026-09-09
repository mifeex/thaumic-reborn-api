package com.thaumicreborn.api.alchemy;
import net.minecraft.world.item.ItemStack;
import java.util.Map;
/** Item decomposition and mutable essentia-store discovery. */
public interface AlchemyApi {
    Map<String, Integer> itemAspects(ItemStack stack);
}
