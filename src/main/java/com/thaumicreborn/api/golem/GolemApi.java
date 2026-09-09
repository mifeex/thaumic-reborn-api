package com.thaumicreborn.api.golem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import java.util.List;
import java.util.Optional;

/** Inspection and bounded configuration of Thaumic Reborn golems. */
public interface GolemApi {
    Optional<GolemState> state(Entity entity);
    boolean setHome(Entity entity, BlockPos position, Direction face, int radius);
    boolean replaceMarkers(Entity entity, List<GolemMarker> markers);
    boolean replaceFilters(Entity entity, List<ItemStack> filters, List<Integer> colors);
    boolean setToggle(Entity entity, int index, boolean enabled);
}
