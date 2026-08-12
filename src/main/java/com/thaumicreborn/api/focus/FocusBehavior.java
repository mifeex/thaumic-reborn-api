package com.thaumicreborn.api.focus;

import net.minecraft.world.InteractionResult;

/** Server-side extension point for custom focus casting. */
public interface FocusBehavior {
    InteractionResult cast(FocusUseContext context);

    /** Return false to stop continuous use. */
    default boolean tick(FocusUseContext context) { return true; }

    default void stopped(FocusUseContext context) { }
}
