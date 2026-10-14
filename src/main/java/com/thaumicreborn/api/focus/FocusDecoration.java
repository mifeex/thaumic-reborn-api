package com.thaumicreborn.api.focus;

import net.minecraft.resources.ResourceLocation;

/**
 * Ornament texture and transforms, shared by all focus renderers.
 * A null texture keeps discovery by addon namespace and focus id.
 * Positive heightOffset moves toward the tip, in local block units.
 * widthScale expands both horizontal axes; extrusionDepth is in unscaled sprite units.
 */
public record FocusDecoration(ResourceLocation texture, float heightOffset,
                              float widthScale, float extrusionDepth) {
    public static final FocusDecoration DEFAULT = new FocusDecoration(null, 0, 1, .05F);

    public FocusDecoration {
        if (!Float.isFinite(heightOffset) || !Float.isFinite(widthScale)
                || !Float.isFinite(extrusionDepth) || widthScale <= 0 || extrusionDepth <= 0) {
            throw new IllegalArgumentException("Focus decoration requires finite offsets and positive dimensions");
        }
    }
}
