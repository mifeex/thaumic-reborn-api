package com.thaumicreborn.api.focus;

import net.minecraft.world.InteractionResult;

/** Server-side extension point for custom focus casting. */
public interface FocusBehavior {
    /** Opt out of automatic single-cast payment when the behavior atomically pays a dynamic cost itself.
     * Such behaviors must call FocusApi.consumeVis after permission checks and before committing effects. */
    default boolean paysOwnVis() { return false; }

    /**
     * Side-effect-free server validation before payment and animation, also checked
     * during continuous use. False rejects the attempt with rate-limited wandfail.
     */
    default boolean canCast(FocusUseContext context) { return true; }

    /** Return SUCCESS/CONSUME on success, FAIL/PASS when no cast was performed. */
    InteractionResult cast(FocusUseContext context);

    /** Return false when continuous use fails; the host stops animation and rate-limits wandfail. */
    default boolean tick(FocusUseContext context) { return true; }

    default void stopped(FocusUseContext context) { }
}
