package com.thaumicreborn.api.research;

import java.util.List;
import java.util.Objects;

/** Stable summary of a research entry without exposing screen internals. */
public record Research(
        String id,
        String categoryId,
        String titleKey,
        String subtitleKey,
        String iconItem,
        List<String> parents,
        boolean concealed,
        boolean inactive,
        boolean virtual
) {
    public Research {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(categoryId, "categoryId");
        Objects.requireNonNull(titleKey, "titleKey");
        Objects.requireNonNull(subtitleKey, "subtitleKey");
        Objects.requireNonNull(iconItem, "iconItem");
        parents = List.copyOf(parents);
    }
}
