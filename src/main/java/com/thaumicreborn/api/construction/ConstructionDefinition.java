package com.thaumicreborn.api.construction;
import net.minecraft.resources.ResourceLocation;
import java.util.Map;
import java.util.Objects;
public record ConstructionDefinition(String id, Handler handler, Trigger trigger,
        String researchId, Map<String, Integer> centivisCost) {
    public ConstructionDefinition {
        Objects.requireNonNull(id, "id"); Objects.requireNonNull(handler, "handler"); Objects.requireNonNull(trigger, "trigger");
        researchId = researchId == null ? "" : researchId; centivisCost = Map.copyOf(centivisCost);
    }
    public enum Handler { RESEARCH_TABLE_PAIR, CRUCIBLE, INFERNAL_FURNACE, INFUSION_ALTAR, THAUMATORIUM, ADVANCED_ALCHEMICAL_FURNACE }
    public enum TriggerType { ITEM, WAND }
    public record Trigger(TriggerType type, ResourceLocation itemId, int consume) {
        public Trigger { Objects.requireNonNull(type, "type"); if (consume < 0) throw new IllegalArgumentException("consume cannot be negative"); }
    }
}
