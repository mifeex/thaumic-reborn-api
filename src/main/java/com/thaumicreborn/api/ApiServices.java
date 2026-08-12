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

/** Main-mod service provider. Addons consume it through {@link ThaumicRebornApi}. */
public interface ApiServices {
    AspectApi aspects();

    ResearchApi research();

    KnowledgeApi knowledge();

    ScanApi scans();

    RecipeApi recipes();

    WandApi wands();

    EssentiaApi essentia();

    AuraApi aura();

    FocusApi foci();

    EquipmentApi equipment();
}
