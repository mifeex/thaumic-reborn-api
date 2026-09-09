package com.thaumicreborn.api.research;
import java.util.List;
import java.util.Objects;
/** Immutable page exactly matching the active datapack/page model. */
public record ResearchPageDefinition(Type type, String titleKey, String bodyKey, String recipeId,
        List<AspectCost> aspectCosts, InfusionDisplay infusionDisplay, List<String> recipeIds,
        String requiredResearchId) {
    public ResearchPageDefinition {
        Objects.requireNonNull(type, "type"); titleKey = titleKey == null ? "" : titleKey;
        bodyKey = bodyKey == null ? "" : bodyKey; recipeId = recipeId == null ? "" : recipeId;
        aspectCosts = List.copyOf(aspectCosts); recipeIds = List.copyOf(recipeIds);
        requiredResearchId = requiredResearchId == null ? "" : requiredResearchId;
    }
    public enum Type { TEXT, RECIPE, COMPOUND_CRAFTING, INFUSION, UNAVAILABLE }
}
