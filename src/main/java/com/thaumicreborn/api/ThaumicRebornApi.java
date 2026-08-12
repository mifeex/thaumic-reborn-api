package com.thaumicreborn.api;

import com.thaumicreborn.api.aspect.AspectApi;
import com.thaumicreborn.api.aura.AuraApi;
import com.thaumicreborn.api.crafting.RecipeApi;
import com.thaumicreborn.api.equipment.EquipmentApi;
import com.thaumicreborn.api.essentia.EssentiaApi;
import com.thaumicreborn.api.focus.FocusApi;
import com.thaumicreborn.api.knowledge.KnowledgeApi;
import com.thaumicreborn.api.research.ResearchApi;
import com.thaumicreborn.api.scan.ScanApi;
import com.thaumicreborn.api.wand.WandApi;

import java.util.Objects;

/**
 * Stable entry point exposed by the Thaumic Reborn main mod.
 *
 * <p>The API artifact is a compile-only dependency. At runtime these exact
 * classes are supplied by the main mod.</p>
 */
public final class ThaumicRebornApi {
    public static final String API_VERSION = "1.1.0";
    public static final String MOD_ID = "thaumcraftmodern";

    private static volatile ApiServices services;

    private ThaumicRebornApi() {
    }

    public static boolean isAvailable() {
        return services != null;
    }

    public static AspectApi aspects() {
        return services().aspects();
    }

    public static ResearchApi research() {
        return services().research();
    }

    public static KnowledgeApi knowledge() {
        return services().knowledge();
    }

    public static ScanApi scans() {
        return services().scans();
    }

    public static RecipeApi recipes() {
        return services().recipes();
    }

    public static WandApi wands() {
        return services().wands();
    }

    public static EssentiaApi essentia() {
        return services().essentia();
    }

    public static AuraApi aura() {
        return services().aura();
    }

    public static FocusApi foci() {
        return services().foci();
    }

    public static EquipmentApi equipment() {
        return services().equipment();
    }

    /**
     * Installs the main-mod implementation. Addons must never call this.
     */
    public static synchronized void install(ApiServices implementation) {
        Objects.requireNonNull(implementation, "implementation");
        if (services != null && services != implementation) {
            throw new IllegalStateException("Thaumic Reborn API is already installed");
        }
        services = implementation;
    }

    private static ApiServices services() {
        ApiServices current = services;
        if (current == null) {
            throw new IllegalStateException(
                    "Thaumic Reborn is not loaded; declare a mandatory dependency on modId '"
                            + MOD_ID + "'"
            );
        }
        return current;
    }
}
