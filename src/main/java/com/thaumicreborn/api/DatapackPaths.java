package com.thaumicreborn.api;

import net.minecraft.resources.ResourceLocation;

/** Authoritative loader folders and recipe serializer IDs for addon data generation. */
public final class DatapackPaths {
    public static final String ASPECTS = "thaumcraft/aspects";
    public static final String CATEGORIES = "thaumcraft/categories";
    public static final String RESEARCH = "thaumcraft/research";
    public static final String SCANS = "thaumcraft/scans";
    public static final String CRUCIBLE_RECIPES = "thaumcraft/crucible_recipes";
    public static final String INFUSION_RECIPES = "thaumcraft/infusion_recipes";
    public static final String WANDS = "thaumcraft/wands";
    public static final String CONSTRUCTIONS = "thaumcraft/constructions";
    public static final String ESSENTIA_TRANSPORTS = "thaumcraft/essentia_transports";

    public static final ResourceLocation ARCANE_SHAPED = id("arcane_shaped");
    public static final ResourceLocation ARCANE_SHAPELESS = id("arcane_shapeless");
    public static final ResourceLocation ARCANE_WAND_ASSEMBLY = id("arcane_wand_assembly");
    public static final ResourceLocation ARCANE_SCEPTRE_ASSEMBLY = id("arcane_sceptre_assembly");
    public static final ResourceLocation DOUBLE_SMELTING = id("double_smelting");
    public static final ResourceLocation DOUBLE_BLASTING = id("double_blasting");
    public static final ResourceLocation KNOWLEDGE_FRAGMENT = id("knowledge_fragment");

    private DatapackPaths() { }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ThaumicRebornApi.MOD_ID, path);
    }
}
