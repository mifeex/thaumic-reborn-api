package com.thaumicreborn.api.golem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
/** Immutable operational/configuration snapshot without entity internals. */
public record GolemState(UUID id, GolemMaterial material, GolemCore core, boolean advanced,
        boolean operational, BlockPos home, Direction homeFace, int homeRadius,
        List<GolemMarker> markers, Map<GolemUpgrade, Integer> upgrades,
        List<ItemStack> filters, List<Integer> filterColors, int toggles) {
    public GolemState {
        Objects.requireNonNull(id, "id"); Objects.requireNonNull(material, "material");
        home = home == null ? null : home.immutable(); homeFace = homeFace == null ? Direction.UP : homeFace;
        markers = List.copyOf(markers); upgrades = Map.copyOf(upgrades);
        filters = filters.stream().map(ItemStack::copy).toList(); filterColors = List.copyOf(filterColors);
    }
    @Override public List<ItemStack> filters() { return filters.stream().map(ItemStack::copy).toList(); }
    public enum GolemCore { FILL, EMPTY, GATHER, HARVEST, GUARD, LIQUID, ALCHEMY, LUMBER, USE, BUTCHER, SORTING, FISHING }
    public enum GolemUpgrade { AER, TERRA, IGNIS, AQUA, ORDO, PERDITIO }
    public enum GolemMaterial { STRAW, WOOD, TALLOW, CLAY, FLESH, STONE, IRON, THAUMIUM }
}
