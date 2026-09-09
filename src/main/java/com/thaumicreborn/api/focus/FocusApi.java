package com.thaumicreborn.api.focus;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FocusApi {
    void register(FocusDefinition definition, FocusBehavior behavior);
    Optional<FocusDefinition> find(ResourceLocation id);
    List<FocusDefinition> all();
    Optional<FocusUpgradeDefinition> upgrade(ResourceLocation id);
    List<FocusUpgradeDefinition> upgrades();
    Optional<FocusState> state(ItemStack focus);
    boolean applyUpgrade(ItemStack focus, ResourceLocation upgradeId, int rank);
    Optional<ItemStack> equipped(ItemStack wand);
    boolean equip(ItemStack wand, ItemStack focus);
    void clear(ItemStack wand);
    FocusPouchApi pouches();
    boolean consumeVis(ServerPlayer player, ItemStack wand, Map<String, Integer> centivisCost);
}
