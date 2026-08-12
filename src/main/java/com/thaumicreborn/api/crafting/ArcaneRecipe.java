package com.thaumicreborn.api.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Public view of an Arcane Workbench recipe. */
public record ArcaneRecipe(
        ResourceLocation id,
        String research,
        List<Ingredient> ingredients,
        ItemStack output,
        Map<String, Integer> vis,
        boolean shaped,
        int width,
        int height
) {
    public ArcaneRecipe {
        Objects.requireNonNull(id, "id");
        research = research == null ? "" : research;
        ingredients = List.copyOf(ingredients);
        output = Objects.requireNonNull(output, "output").copy();
        vis = Map.copyOf(vis);
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("recipe dimensions cannot be negative");
        }
    }

    @Override
    public ItemStack output() {
        return output.copy();
    }
}
