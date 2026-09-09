package com.thaumicreborn.api.research;
import java.util.List;
import java.util.Objects;
/** Data-only mirror of every condition accepted by the research loader. */
public sealed interface ResearchCondition permits ResearchCondition.Always,
        ResearchCondition.AllOf, ResearchCondition.AnyOf, ResearchCondition.Not,
        ResearchCondition.ResearchCompleted, ResearchCondition.ResearchRevealed,
        ResearchCondition.ScanCompleted, ResearchCondition.ScannedAspect,
        ResearchCondition.AspectKnown, ResearchCondition.AspectAmount,
        ResearchCondition.WarpAtLeast, ResearchCondition.CriterionRecorded {
    record Always() implements ResearchCondition { }
    record AllOf(List<ResearchCondition> conditions) implements ResearchCondition { public AllOf { conditions = copy(conditions, false); } }
    record AnyOf(List<ResearchCondition> conditions) implements ResearchCondition { public AnyOf { conditions = copy(conditions, true); } }
    record Not(ResearchCondition condition) implements ResearchCondition { public Not { Objects.requireNonNull(condition, "condition"); } }
    record ResearchCompleted(String researchId) implements ResearchCondition { public ResearchCompleted { researchId = id(researchId, "researchId"); } }
    record ResearchRevealed(String researchId) implements ResearchCondition { public ResearchRevealed { researchId = id(researchId, "researchId"); } }
    record ScanCompleted(String scanId) implements ResearchCondition { public ScanCompleted { scanId = id(scanId, "scanId"); } }
    record ScannedAspect(String aspectId) implements ResearchCondition { public ScannedAspect { aspectId = id(aspectId, "aspectId"); } }
    record AspectKnown(String aspectId) implements ResearchCondition { public AspectKnown { aspectId = id(aspectId, "aspectId"); } }
    record AspectAmount(String aspectId, int minimum) implements ResearchCondition {
        public AspectAmount { aspectId = id(aspectId, "aspectId"); if (minimum < 1) throw new IllegalArgumentException("minimum must be positive"); }
    }
    record WarpAtLeast(WarpMeasure measure, int minimum) implements ResearchCondition {
        public WarpAtLeast { Objects.requireNonNull(measure, "measure"); if (minimum < 0) throw new IllegalArgumentException("minimum cannot be negative"); }
    }
    record CriterionRecorded(String criterionId) implements ResearchCondition { public CriterionRecorded { criterionId = id(criterionId, "criterionId"); } }
    enum WarpMeasure { PERMANENT, NORMAL, TEMPORARY, NON_TEMPORARY, TOTAL }
    private static List<ResearchCondition> copy(List<ResearchCondition> source, boolean nonEmpty) {
        List<ResearchCondition> result = List.copyOf(Objects.requireNonNull(source, "conditions"));
        if ((nonEmpty && result.isEmpty()) || result.stream().anyMatch(Objects::isNull)) throw new IllegalArgumentException("invalid condition list");
        return result;
    }
    private static String id(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank() || !value.equals(value.trim())) throw new IllegalArgumentException(name + " must be non-blank and trimmed");
        return value;
    }
}
