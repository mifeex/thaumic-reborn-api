package com.thaumicreborn.api.focus;

import net.minecraft.resources.ResourceLocation;

/** Compatibility contract for equippable foci. New items should extend AbstractFocusItem. */
public interface FocusItem {
    ResourceLocation focusId();

    /** Legacy items retain automatic ornament discovery and the original transform. */
    default FocusDecoration decoration() { return FocusDecoration.DEFAULT; }
}
