package com.thaumicreborn.api.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record InfusionRecipe(
        ResourceLocation id,
        String research,
        int instability,
        Ingredient central,
        List<Ingredient> components,
        ItemStack output,
        Map<String, Integer> essentia
) {
    public InfusionRecipe {
        Objects.requireNonNull(id, "id");
        research = research == null ? "" : research;
        Objects.requireNonNull(central, "central");
        components = List.copyOf(components);
        output = Objects.requireNonNull(output, "output").copy();
        essentia = Map.copyOf(essentia);
        if (instability < 0) throw new IllegalArgumentException("instability cannot be negative");
    }

    @Override
    public ItemStack output() {
        return output.copy();
    }
}
