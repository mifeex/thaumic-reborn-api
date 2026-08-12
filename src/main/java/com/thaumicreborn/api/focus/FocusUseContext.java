package com.thaumicreborn.api.focus;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

/** Server-side state supplied to addon focus behavior. */
public record FocusUseContext(ServerPlayer player, ItemStack wand, ItemStack focus,
                              InteractionHand hand, Optional<BlockHitResult> blockHit) {
    public FocusUseContext {
        blockHit = blockHit == null ? Optional.empty() : blockHit;
    }
}
