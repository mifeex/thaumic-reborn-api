package com.thaumicreborn.api.research;
import java.util.List;
import java.util.Objects;
/** Complete immutable public view of an active research definition. */
public record ResearchDefinition(String id, String categoryId, String iconItem, String iconResource,
        String titleKey, String subtitleKey, boolean concealed, boolean autoUnlock, boolean inactive,
        boolean virtual, String revealedBy, List<String> parents, List<String> hiddenParents,
        ResearchCondition revealWhen, ResearchCondition unlockWhen, int x, int y,
        List<ResearchPageDefinition> pages, int completionWarp, NodeFrame nodeFrame,
        boolean specialFrame, List<AspectCost> researchCost, List<AspectCost> purchaseCost,
        List<String> siblings) {
    public ResearchDefinition {
        Objects.requireNonNull(id, "id"); Objects.requireNonNull(categoryId, "categoryId");
        iconItem = iconItem == null ? "" : iconItem; iconResource = iconResource == null ? "" : iconResource;
        Objects.requireNonNull(titleKey, "titleKey"); subtitleKey = subtitleKey == null ? "" : subtitleKey;
        revealedBy = revealedBy == null ? "" : revealedBy; parents = List.copyOf(parents);
        hiddenParents = List.copyOf(hiddenParents); Objects.requireNonNull(revealWhen, "revealWhen");
        Objects.requireNonNull(unlockWhen, "unlockWhen"); pages = List.copyOf(pages);
        Objects.requireNonNull(nodeFrame, "nodeFrame"); researchCost = List.copyOf(researchCost);
        purchaseCost = List.copyOf(purchaseCost); siblings = List.copyOf(siblings);
        if (completionWarp < 0) throw new IllegalArgumentException("completionWarp cannot be negative");
    }
    public boolean purchasable() { return !purchaseCost.isEmpty(); }
    public enum NodeFrame { PRIMARY, ROUND, SECONDARY, HIDDEN }
}
