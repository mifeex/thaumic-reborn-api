package com.thaumicreborn.api.research;
import java.util.List;
import java.util.Objects;
/** Presentation-only infusion layout embedded in a research page. */
public record InfusionDisplay(String outputItem, String centralItem, List<Component> components,
        Instability instability, String detailKey, boolean showDurability) {
    public InfusionDisplay {
        Objects.requireNonNull(outputItem, "outputItem"); Objects.requireNonNull(centralItem, "centralItem");
        components = List.copyOf(components); Objects.requireNonNull(instability, "instability");
        detailKey = detailKey == null ? "" : detailKey;
    }
    public record Component(String itemId, String tagId, int count, String potionId) {
        public Component {
            itemId = itemId == null ? "" : itemId; tagId = tagId == null ? "" : tagId; potionId = potionId == null ? "" : potionId;
            if (itemId.isBlank() == tagId.isBlank() || count <= 0) throw new IllegalArgumentException("invalid infusion component");
        }
        public boolean isTag() { return !tagId.isBlank(); }
    }
    public enum Instability { NEGLIGIBLE, MINOR, MODERATE, HIGH, VERY_HIGH, DANGEROUS }
}
