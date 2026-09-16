package com.thaumicreborn.api.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;

/** Client-thread visual helpers. Never use these positions for server damage or collision. */
public interface FocusEffectsApi {
    FocusEffectsApi EMPTY = new FocusEffectsApi() {};

    /**
     * Last rendered upper cap (or outer focus face) in world coordinates.
     * Empty for remote players, third person, stale frames, or an unrendered hand.
     * Uses the previous render frame and is suitable for starting client particles.
     */
    default Optional<Vec3> renderedTip(Player caster, InteractionHand hand) { return Optional.empty(); }

    /**
     * Tip in the supplied pose's local coordinates during a world render pass.
     * Pass the same pose used to draw the beam; its projection must be the active world projection.
     * Captured hand projection is converted to the world projection, including differing FOVs.
     */
    default Optional<Vec3> renderedTip(Player caster, InteractionHand hand, PoseStack worldPose) {
        return Optional.empty();
    }

    /** Call before advancing an addon projectile on the client to capture its visual launch offset. */
    default void beginProjectileTick(Projectile projectile) { }

    /** Add to the entity renderer's offset. Converges to zero without moving the actual entity. */
    default Vec3 projectileOffset(Projectile projectile, float partialTick) { return Vec3.ZERO; }

    /** Call after advancing the projectile on the client, instead of its ordinary trail emission. */
    default void projectileTrail(Projectile projectile, ParticleOptions particle) {
        projectile.level().addParticle(particle, projectile.getX(), projectile.getY(), projectile.getZ(), 0, 0, 0);
    }
}
