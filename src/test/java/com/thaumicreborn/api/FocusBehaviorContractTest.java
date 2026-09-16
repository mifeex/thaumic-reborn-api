package com.thaumicreborn.api;

import com.thaumicreborn.api.focus.FocusBehavior;
import com.thaumicreborn.api.focus.FocusUseContext;
import net.minecraft.world.InteractionResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FocusBehaviorContractTest {
    @Test void legacyBehaviorKeepsItsDefaultValidation() throws Exception {
        FocusBehavior legacy = context -> InteractionResult.CONSUME;
        assertTrue(legacy.canCast(null));
        assertTrue(FocusBehavior.class.getMethod("canCast", FocusUseContext.class).isDefault());
    }
    @Test void addonCanRejectBeforeItsCastRuns() {
        FocusBehavior rejecting = new FocusBehavior() {
            public boolean canCast(FocusUseContext context) { return false; }
            public InteractionResult cast(FocusUseContext context) { throw new AssertionError("Must not cast"); }
        };
        assertFalse(rejecting.canCast(null));
    }
}
