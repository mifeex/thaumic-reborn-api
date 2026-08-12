package com.thaumicreborn.api.client;

import net.minecraft.world.phys.Vec3;

import java.util.List;

public record HudReadout(List<HudAspect> aspects, Vec3 anchor) {
    public HudReadout {
        aspects = List.copyOf(aspects);
        if (anchor == null) throw new IllegalArgumentException("anchor is required");
    }
}
