package com.thaumicreborn.api.research;
import java.util.Objects;
/** Immutable category metadata loaded from thaumcraft/categories. */
public record ResearchCategory(String id, String titleKey, String iconItem, String iconResource,
        String backgroundTexture, int order) {
    public ResearchCategory {
        Objects.requireNonNull(id, "id"); Objects.requireNonNull(titleKey, "titleKey");
        iconItem = iconItem == null ? "" : iconItem; iconResource = iconResource == null ? "" : iconResource;
        Objects.requireNonNull(backgroundTexture, "backgroundTexture");
    }
}
