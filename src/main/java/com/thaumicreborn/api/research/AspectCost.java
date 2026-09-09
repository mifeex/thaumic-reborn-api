package com.thaumicreborn.api.research;
import java.util.Objects;
/** Immutable aspect-point cost used by research and research pages. */
public record AspectCost(String aspectId, int amount) {
    public AspectCost {
        Objects.requireNonNull(aspectId, "aspectId");
        if (aspectId.isBlank() || amount <= 0) throw new IllegalArgumentException("invalid aspect cost");
    }
}
