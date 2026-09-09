package com.thaumicreborn.api.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;

import java.util.List;
import java.util.Optional;

/** Read-only view of Thaumic Reborn's non-vanilla recipe catalogs. */
public interface RecipeApi {
    List<ArcaneRecipe> arcaneRecipes(MinecraftServer server);
    Optional<ArcaneRecipe> arcaneRecipe(MinecraftServer server, ResourceLocation id);

    List<CrucibleRecipe> crucibleRecipes();
    Optional<CrucibleRecipe> crucibleRecipe(ResourceLocation id);

    List<InfusionRecipe> infusionRecipes();

    Optional<InfusionRecipe> infusionRecipe(ResourceLocation id);
}
