package com.thaumicreborn.api.client;

public interface ClientApiServices {
    AspectRenderApi aspects();
    HudApi hud();
    /** Default preserves compatibility with older service implementations. */
    default FocusEffectsApi focusEffects() { return FocusEffectsApi.EMPTY; }
}
