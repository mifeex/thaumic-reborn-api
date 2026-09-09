package com.thaumicreborn.api.equipment;

import net.minecraft.world.item.ItemStack;

/**
 * Leg armor whose rendered waist rises into the lower chest-armor region.
 *
 * <p>Thaumic Reborn uses this contract to suppress only its conflicting chest
 * waist geometry. Implementing the interface is sufficient for an unconditional
 * raised waist; stack-dependent items may override {@link #hasRaisedWaist(ItemStack)}.
 * Consumers are responsible for checking that the stack is worn as leggings.</p>
 */
public interface RaisedWaistArmor {
    default boolean hasRaisedWaist(ItemStack stack) {
        return true;
    }
}
