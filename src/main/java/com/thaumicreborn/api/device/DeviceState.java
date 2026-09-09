package com.thaumicreborn.api.device;
import java.util.Map;
import java.util.Objects;
/** Generic state shared without exposing block-entity classes or persistence tags. */
public record DeviceState(DeviceKind kind, boolean active, int progress, int capacity,
        String aspectId, int essentiaAmount, Map<String, Integer> values) {
    public DeviceState {
        Objects.requireNonNull(kind, "kind"); aspectId = aspectId == null ? "" : aspectId; values = Map.copyOf(values);
    }
    public enum DeviceKind { CRUCIBLE, ALEMBIC, ALCHEMICAL_FURNACE, ADVANCED_ALCHEMICAL_FURNACE,
        THAUMATORIUM, INFUSION_MATRIX, FOCAL_MANIPULATOR, ESSENTIA_JAR, VOID_JAR,
        ESSENTIA_RESERVOIR, ESSENTIA_BUFFER, ADVANCED_ESSENTIA_BUFFER, CENTRIFUGE,
        CRYSTALLIZER, FLUX_SCRUBBER, ARCANE_BORE, RECHARGE_PEDESTAL, OTHER }
}
