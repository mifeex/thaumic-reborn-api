package com.thaumicreborn.api;

import net.minecraft.resources.ResourceLocation;

/** Authoritative loader folders and recipe serializer IDs for addon data generation. */
public final class DatapackPaths {
    public static final String ASPECTS = "arcana/aspects";
    public static final String CATEGORIES = "arcana/categories";
    public static final String RESEARCH = "arcana/research";
    public static final String SCANS = "arcana/scans";
    public static final String CRUCIBLE_RECIPES = "arcana/crucible_recipes";
    public static final String INFUSION_RECIPES = "arcana/infusion_recipes";
    public static final String WANDS = "arcana/wands";
    public static final String CONSTRUCTIONS = "arcana/constructions";
    public static final String ESSENTIA_TRANSPORTS = "arcana/essentia_transports";

    public static final ResourceLocation ARCANE_SHAPED = id("arcane_shaped");
    public static final ResourceLocation ARCANE_SHAPELESS = id("arcane_shapeless");
    public static final ResourceLocation ARCANE_WAND_ASSEMBLY = id("arcane_wand_assembly");
    public static final ResourceLocation ARCANE_SCEPTRE_ASSEMBLY = id("arcane_sceptre_assembly");
    public static final ResourceLocation DOUBLE_SMELTING = id("double_smelting");
    public static final ResourceLocation DOUBLE_BLASTING = id("double_blasting");
    public static final ResourceLocation KNOWLEDGE_FRAGMENT = id("knowledge_fragment_research");

    private DatapackPaths() { }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ThaumicRebornApi.MOD_ID, path);
    }
}
