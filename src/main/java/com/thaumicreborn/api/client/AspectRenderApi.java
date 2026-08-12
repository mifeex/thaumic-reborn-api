package com.thaumicreborn.api.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

/** Draws aspect icons with Thaumic Reborn's textures, tint, and typography. */
public interface AspectRenderApi {
    boolean draw(GuiGraphics graphics, String aspectId, int x, int y, int size, float alpha);
    boolean drawTag(GuiGraphics graphics, Font font, String aspectId,
                    int x, int y, int size, int amount, float alpha);
    String formatVis(int centivis);
}
