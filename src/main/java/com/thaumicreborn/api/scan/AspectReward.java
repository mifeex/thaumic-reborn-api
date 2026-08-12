package com.thaumicreborn.api.scan;

import java.util.Objects;

public record AspectReward(String aspectId, int amount) {
    public AspectReward {
        Objects.requireNonNull(aspectId, "aspectId");
        if (aspectId.isBlank()) throw new IllegalArgumentException("aspectId cannot be blank");
        if (amount <= 0) throw new IllegalArgumentException("amount must be positive");
    }
}
