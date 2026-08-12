package com.thaumicreborn.api.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Map;
import java.util.Objects;

public record CrucibleRecipe(
        ResourceLocation id,
        String research,
        Ingredient catalyst,
        String catalystAspect,
        ItemStack output,
        Map<String, Integer> essentia
) {
    public CrucibleRecipe {
        Objects.requireNonNull(id, "id");
        research = research == null ? "" : research;
        Objects.requireNonNull(catalyst, "catalyst");
        catalystAspect = catalystAspect == null ? "" : catalystAspect;
        output = Objects.requireNonNull(output, "output").copy();
        essentia = Map.copyOf(essentia);
    }

    @Override
    public ItemStack output() {
        return output.copy();
    }
}
