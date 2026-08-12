package com.thaumicreborn.api.wand;

import java.util.Map;
import java.util.Objects;

/** Immutable wand state. Vis values are centivis: 100 centivis is one vis. */
public record WandState(
        String rodId,
        String capId,
        Map<String, Integer> visCentivis,
        int capacityVis,
        WandForm form
) {
    public WandState {
        Objects.requireNonNull(rodId, "rodId");
        Objects.requireNonNull(capId, "capId");
        visCentivis = Map.copyOf(visCentivis);
        Objects.requireNonNull(form, "form");
    }
}
