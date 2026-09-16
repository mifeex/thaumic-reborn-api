package com.thaumicreborn.api.wand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
/** Implement on a targeted block entity to receive held-wand use ticks.
 * The block must start item use when interacted with. Return true only while handling it. */
public interface WandChannel {
    boolean onWandUseTick(Player player, ItemStack wand);
}
