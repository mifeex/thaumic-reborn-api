package com.thaumicreborn.api.wand;

import java.util.List;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

public record WandRod(
        String id,
        int capacityVis,
        String translationKey,
        String researchId,
        List<String> rechargeAspects,
        boolean staff,
        boolean runes,
        ResourceLocation texture
) {
    public WandRod {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(translationKey, "translationKey");
        Objects.requireNonNull(researchId, "researchId");
        Objects.requireNonNull(texture, "texture");
        rechargeAspects = List.copyOf(rechargeAspects);
    }

    /**
     * Retains the legacy constructor and applies the main mod's conventional
     * texture path. Data-driven components should use the canonical
     * constructor so addon namespaces and custom paths are preserved.
     */
    public WandRod(
            String id,
            int capacityVis,
            String translationKey,
            String researchId,
            List<String> rechargeAspects,
            boolean staff,
            boolean runes
    ) {
        this(id, capacityVis, translationKey, researchId, rechargeAspects,
                staff, runes, defaultTexture(id));
    }

    private static ResourceLocation defaultTexture(String id) {
        boolean staff = id.endsWith("_staff");
        String base = staff ? id.substring(0, id.length() - "_staff".length()) : id;
        if (base.equals("codex")) {
            base = "silverwood";
        }
        String name = staff
                ? "staff_rod_" + base + "_model"
                : "wand_rod_" + base + "_model";
        return ResourceLocation.fromNamespaceAndPath(
                "thaumic_reborn",
                "textures/item/" + name + ".png"
        );
    }
}
