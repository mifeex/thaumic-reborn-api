package com.thaumicreborn.api;

import com.thaumicreborn.api.knowledge.KnowledgeSnapshot;
import com.thaumicreborn.api.knowledge.WarpType;
import com.thaumicreborn.api.research.ResearchDefinition;
import com.thaumicreborn.api.focus.FocusDefinition;
import com.thaumicreborn.api.aura.AuraNode;
import com.thaumicreborn.api.equipment.RaisedWaistArmor;
import net.minecraft.world.item.ItemStack;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ApiContractTest {
    @Test
    void versionAndModIdMatchTheMajorContract() {
        assertEquals("2.0.0", ThaumicRebornApi.API_VERSION);
        assertEquals("thaumic_reborn", ThaumicRebornApi.MOD_ID);
    }

    @Test
    void researchDtoCannotSilentlyLoseCurrentFields() {
        List<String> names = java.util.Arrays.stream(ResearchDefinition.class.getRecordComponents())
                .map(java.lang.reflect.RecordComponent::getName).toList();
        assertEquals(List.of("id", "categoryId", "iconItem", "iconResource", "titleKey",
                "subtitleKey", "concealed", "autoUnlock", "inactive", "virtual",
                "revealedBy", "parents", "hiddenParents", "revealWhen", "unlockWhen",
                "x", "y", "pages", "completionWarp", "nodeFrame", "specialFrame",
                "researchCost", "purchaseCost", "siblings"), names);
        assertEquals(List.of("id", "color", "continuous", "cooldownTicks",
                "centivisCost", "perTickCost", "maximumRanks", "upgradesByRank"),
                componentNames(FocusDefinition.class));
        assertEquals(List.of("id", "type", "modifier", "current", "maximum", "revision"),
                componentNames(AuraNode.class));
        assertEquals(List.of("knownAspects", "aspectAmounts", "completedScans",
                "revealedResearch", "completedResearch", "researchCriteria", "warp",
                "warpCounter", "runicCharge"), componentNames(KnowledgeSnapshot.class));
    }

    @Test
    void knowledgeSnapshotDefensivelyCopiesState() {
        Map<String, Integer> amounts = new LinkedHashMap<>();
        amounts.put("aer", 5);
        EnumMap<WarpType, Integer> warp = new EnumMap<>(WarpType.class);
        warp.put(WarpType.PERMANENT, 2);
        KnowledgeSnapshot snapshot = new KnowledgeSnapshot(Set.of("aer"), amounts,
                Set.of(), Set.of(), Set.of(), Set.of(), warp, 2, 0);
        amounts.put("aer", 99);
        warp.put(WarpType.NORMAL, 10);
        assertEquals(5, snapshot.aspectAmounts().get("aer"));
        assertEquals(2, snapshot.totalWarp());
        assertThrows(UnsupportedOperationException.class,
                () -> snapshot.aspectAmounts().put("ordo", 1));
    }

    @Test
    void documentationUsesCurrentRecipeNamespaceAndNoLegacyModId() throws Exception {
        String docs = Files.readString(Path.of("README.md"))
                + Files.readString(Path.of("ADDON_SETUP.md"));
        assertTrue(docs.contains("thaumic_reborn:arcane_shaped"));
        assertFalse(docs.contains("thaumcraft" + "modern"));
        assertEquals("thaumic_reborn:arcane_shaped", DatapackPaths.ARCANE_SHAPED.toString());
        assertEquals("thaumcraft/research", DatapackPaths.RESEARCH);
    }

    @Test
    void raisedWaistArmorIsACommonStackAwareMarker() throws Exception {
        var method = RaisedWaistArmor.class.getDeclaredMethod(
                "hasRaisedWaist", ItemStack.class);
        assertEquals(boolean.class, method.getReturnType());
        assertTrue(method.isDefault());
        assertTrue(new RaisedWaistArmor() { }.hasRaisedWaist(null));

        RaisedWaistArmor stackAware = new RaisedWaistArmor() {
            @Override
            public boolean hasRaisedWaist(ItemStack stack) {
                return false;
            }
        };
        assertFalse(stackAware.hasRaisedWaist(null));

        String source = Files.readString(Path.of(
                "src/main/java/com/thaumicreborn/api/equipment/RaisedWaistArmor.java"));
        assertTrue(source.contains("net.minecraft.world.item.ItemStack"));
        assertFalse(source.contains("net.minecraft.client"));
    }

    private static List<String> componentNames(Class<?> type) {
        return java.util.Arrays.stream(type.getRecordComponents())
                .map(java.lang.reflect.RecordComponent::getName).toList();
    }
}
