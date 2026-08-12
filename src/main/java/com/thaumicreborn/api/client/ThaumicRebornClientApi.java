package com.thaumicreborn.api.client;

import java.util.Objects;

/** Client-only entry point. Never reference this class from dedicated-server code. */
public final class ThaumicRebornClientApi {
    private static volatile ClientApiServices services;

    private ThaumicRebornClientApi() { }

    public static boolean isAvailable() { return services != null; }
    public static AspectRenderApi aspects() { return services().aspects(); }
    public static HudApi hud() { return services().hud(); }

    public static synchronized void install(ClientApiServices implementation) {
        Objects.requireNonNull(implementation, "implementation");
        if (services != null && services != implementation)
            throw new IllegalStateException("Thaumic Reborn client API is already installed");
        services = implementation;
    }

    private static ClientApiServices services() {
        if (services == null)
            throw new IllegalStateException("Thaumic Reborn client API is not initialized");
        return services;
    }
}
