package com.thaumicreborn.api.client;

/** One aspect amount rendered by the shared revealing-gear HUD. */
public record HudAspect(String aspectId, int amount) {
    public HudAspect {
        if (aspectId == null || aspectId.isBlank())
            throw new IllegalArgumentException("aspectId must not be blank");
        if (amount <= 0) throw new IllegalArgumentException("amount must be positive");
    }
}
