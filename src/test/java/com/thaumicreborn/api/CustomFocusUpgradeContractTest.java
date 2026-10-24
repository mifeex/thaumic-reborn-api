package com.thaumicreborn.api;
import com.thaumicreborn.api.focus.*;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class CustomFocusUpgradeContractTest {
    private static final ResourceLocation ID=ResourceLocation.fromNamespaceAndPath("addon","upgrade");
    private static FocusUpgradeDefinition definition(int legacy,Map<String,Integer> cost){return new FocusUpgradeDefinition(ID,legacy,ID,"name","text",cost);}
    @Test void customIdentityDoesNotRequireLegacyNumericalSlot(){assertEquals(-1,definition(-1,Map.of("telum",8)).legacyId());assertThrows(IllegalArgumentException.class,()->definition(-2,Map.of("telum",8)));}
    @Test void customCostsAreValidatedAndImmutable(){var cost=new HashMap<String,Integer>();cost.put("telum",8);var d=definition(-1,cost);cost.put("telum",99);assertEquals(8,d.aspectCost().get("telum"));assertThrows(UnsupportedOperationException.class,()->d.aspectCost().put("aer",1));assertThrows(IllegalArgumentException.class,()->definition(-1,Map.of("telum",0)));assertThrows(IllegalArgumentException.class,()->definition(-1,Map.of(" ",8)));}
    @Test void oldBehaviorsKeepAutomaticVisPayment(){assertFalse(new FocusBehavior(){public net.minecraft.world.InteractionResult cast(FocusUseContext c){return net.minecraft.world.InteractionResult.PASS;}}.paysOwnVis());}
}
