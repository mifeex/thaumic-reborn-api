package com.thaumicreborn.api.client;

import net.minecraft.core.BlockPos;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public interface HudApi {
    <T extends BlockEntity> void registerAspectContainer(
            Class<T> type, HudContainerAdapter<T> adapter);
    void drawAspectHud(GuiGraphics graphics, List<HudAspect> aspects,
                       int centerX, int bottomY, float alpha, float scale);
    Vec3 onHitFace(BlockHitResult hit);
    Vec3 aboveBlock(BlockPos position, double height);
}
