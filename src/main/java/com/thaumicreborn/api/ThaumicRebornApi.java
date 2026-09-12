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
import com.thaumicreborn.api.alchemy.AlchemyApi;
import com.thaumicreborn.api.construction.ConstructionApi;
import com.thaumicreborn.api.device.DeviceApi;
import com.thaumicreborn.api.golem.GolemApi;
import com.thaumicreborn.api.link.LinkApi;
import com.thaumicreborn.api.vis.VisNetworkApi;
import com.thaumicreborn.api.world.WorldApi;

import java.util.Objects;

/**
 * Stable entry point exposed by the Thaumic Reborn main mod.
 *
 * <p>The API artifact is a compile-only dependency. At runtime these exact
 * classes are supplied by the main mod.</p>
 */
public final class ThaumicRebornApi {
    public static final String API_VERSION = "2.0.2";
    public static final String MOD_ID = "thaumic_reborn";

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

    public static VisNetworkApi visNetwork() { return services().visNetwork(); }
    public static GolemApi golems() { return services().golems(); }
    public static WorldApi world() { return services().world(); }
    public static ConstructionApi constructions() { return services().constructions(); }
    public static LinkApi links() { return services().links(); }
    public static DeviceApi devices() { return services().devices(); }
    public static AlchemyApi alchemy() { return services().alchemy(); }

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
