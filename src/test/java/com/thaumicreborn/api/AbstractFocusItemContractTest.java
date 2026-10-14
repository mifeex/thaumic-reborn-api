package com.thaumicreborn.api;

import com.thaumicreborn.api.focus.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Modifier;
import static org.junit.jupiter.api.Assertions.*;

class AbstractFocusItemContractTest {
    @Test void baseIsSharedAndKeepsLegacyInterface() throws Exception {
        assertTrue(Modifier.isAbstract(AbstractFocusItem.class.getModifiers()));
        assertEquals(Item.class, AbstractFocusItem.class.getSuperclass());
        assertTrue(FocusItem.class.isAssignableFrom(AbstractFocusItem.class));
        assertEquals(FocusDefinition.class, AbstractFocusItem.class.getMethod("definition").getReturnType());
        assertTrue(Modifier.isFinal(AbstractFocusItem.class.getMethod("focusId").getModifiers()));
        assertTrue(FocusApi.class.getMethod("register", AbstractFocusItem.class, FocusBehavior.class).isDefault());
    }

    @Test void oldAddonItemsKeepDefaultDecorationWithoutRecompilation() {
        FocusItem legacy = () -> new ResourceLocation("example", "focus");
        assertSame(FocusDecoration.DEFAULT, legacy.decoration());
        assertNull(legacy.decoration().texture());
        assertEquals(0, legacy.decoration().heightOffset());
        assertEquals(1, legacy.decoration().widthScale());
        assertEquals(.05F, legacy.decoration().extrusionDepth());
    }

    @Test void decorationRejectsInvalidGeometryButAllowsEitherHeightDirection() {
        assertThrows(IllegalArgumentException.class, () -> new FocusDecoration(null, Float.NaN, 1, .05F));
        assertThrows(IllegalArgumentException.class, () -> new FocusDecoration(null, 0, Float.POSITIVE_INFINITY, .05F));
        assertThrows(IllegalArgumentException.class, () -> new FocusDecoration(null, 0, 0, .05F));
        assertThrows(IllegalArgumentException.class, () -> new FocusDecoration(null, 0, 1, -.1F));
        assertDoesNotThrow(() -> new FocusDecoration(new ResourceLocation("addon", "textures/custom.png"), -.125F, 1.35F, .1F));
    }
}
