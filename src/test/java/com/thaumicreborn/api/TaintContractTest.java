package com.thaumicreborn.api;

import com.thaumicreborn.api.world.*;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaintContractTest {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("addon", "tainted_mob");

    @Test void rejectsInconsistentAutomaticBehaviour() {
        assertThrows(IllegalArgumentException.class, () -> new TaintMobDefinition(ID, -1, false, 160));
        assertThrows(IllegalArgumentException.class, () -> new TaintMobDefinition(ID, 20, false, -1));
        assertThrows(IllegalArgumentException.class, () -> new TaintMobDefinition(ID, 0, true, 160));
        assertThrows(NullPointerException.class, () -> new TaintMobDefinition(null, 20, true, 160));
        assertDoesNotThrow(() -> new TaintMobDefinition(ID, 0, false, 0));
    }

    @Test void oldProvidersDeclineRegistrationsExplicitly() {
        assertThrows(UnsupportedOperationException.class,
                () -> TaintApi.UNSUPPORTED.registerMob(new TaintMobDefinition(ID, 20, true, 160)));
        assertThrows(UnsupportedOperationException.class, () -> TaintApi.UNSUPPORTED.registerSpreadSource(ID));
        assertThrows(UnsupportedOperationException.class, () -> TaintApi.UNSUPPORTED.registerMobConversion(ID, ID));
    }

    @Test void callbackDefinitionsRejectNullsBeforeRegistration() {
        assertThrows(NullPointerException.class, () -> new TaintBlockConversion(null, state -> true, state -> state));
        assertThrows(NullPointerException.class, () -> new TaintBlockConversion(ID, null, state -> state));
        assertThrows(NullPointerException.class, () -> new TaintBlockConversion(ID, state -> true, null));
    }
}
