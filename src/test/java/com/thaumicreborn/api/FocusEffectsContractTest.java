package com.thaumicreborn.api;

import com.thaumicreborn.api.client.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FocusEffectsContractTest {
    @Test
    void oldClientServiceImplementationKeepsWorking() {
        ClientApiServices old = new ClientApiServices() {
            public AspectRenderApi aspects() { return null; }
            public HudApi hud() { return null; }
        };
        assertSame(FocusEffectsApi.EMPTY, old.focusEffects());
        assertTrue(old.focusEffects().renderedTip(null, InteractionHand.MAIN_HAND).isEmpty());
        assertEquals(Vec3.ZERO, old.focusEffects().projectileOffset(null, 0));
    }

    @Test
    void exposesSeparateHandAndWorldPoseContracts() throws Exception {
        assertTrue(FocusEffectsApi.class.getMethod("renderedTip", Player.class,
                InteractionHand.class).isDefault());
        assertTrue(FocusEffectsApi.class.getMethod("renderedTip", Player.class,
                InteractionHand.class, PoseStack.class).isDefault());
        assertEquals(Vec3.class, FocusEffectsApi.class.getMethod("projectileOffset",
                Projectile.class, float.class).getReturnType());
        assertEquals(FocusEffectsApi.class,
                ThaumicRebornClientApi.class.getMethod("focusEffects").getReturnType());
    }
}
