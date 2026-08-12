package com.thaumicreborn.example;

import com.thaumicreborn.api.ThaumicRebornApi;
import com.thaumicreborn.api.focus.FocusDefinition;
import com.thaumicreborn.api.focus.FocusItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/** Minimal addon proving that only the compile-time API is required. */
@Mod(ExampleAddon.MOD_ID)
public final class ExampleAddon {
    public static final String MOD_ID = "thaumic_reborn_example";
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<Item> ECHO_FOCUS = ITEMS.register(
            "echo_focus", () -> new ExampleFocusItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> THAUMIC_BLADE = ITEMS.register(
            "thaumic_blade", () -> new ExampleThaumicBlade());
    public static final RegistryObject<Item> REVEALING_HELMET = ITEMS.register(
            "revealing_helmet", () -> new ExampleRevealingHelmet());

    public ExampleAddon(FMLJavaModLoadingContext context) {
        ITEMS.register(context.getModEventBus());
        context.getModEventBus().addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            int aspectCount = ThaumicRebornApi.aspects().all().size();
            int researchCount = ThaumicRebornApi.research().all().size();
            ThaumicRebornApi.foci().register(
                    new FocusDefinition(id("echo_focus"), 0xB06CFF, false,
                            10, java.util.Map.of("aer", 25, "ordo", 10)),
                    context -> {
                        context.player().serverLevel().playSound(null,
                                context.player().blockPosition(),
                                SoundEvents.AMETHYST_BLOCK_RESONATE,
                                SoundSource.PLAYERS, 0.7F, 1.2F);
                        return InteractionResult.CONSUME;
                    });
            System.out.printf(
                    "Thaumic Reborn API %s: %d aspects, %d research entries%n",
                    ThaumicRebornApi.API_VERSION,
                    aspectCount,
                    researchCount
            );
        });
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private static final class ExampleFocusItem extends Item implements FocusItem {
        private ExampleFocusItem(Properties properties) { super(properties); }
        @Override public ResourceLocation focusId() { return id("echo_focus"); }
    }

    private static final class ExampleThaumicBlade extends SwordItem
            implements com.thaumicreborn.api.equipment.ThaumicRepairable {
        private ExampleThaumicBlade() {
            super(Tiers.IRON, 4, -2.4F, new Item.Properties().durability(480));
        }
    }

    private static final class ExampleRevealingHelmet extends ArmorItem implements
            com.thaumicreborn.api.equipment.RevealingGear,
            com.thaumicreborn.api.equipment.VisDiscountGear,
            com.thaumicreborn.api.equipment.RunicArmor,
            com.thaumicreborn.api.equipment.ThaumicRepairable {
        private ExampleRevealingHelmet() {
            super(ArmorMaterials.IRON, Type.HELMET,
                    new Item.Properties().durability(220));
        }
        @Override public boolean reveals(ItemStack stack) { return true; }
        @Override public int visDiscountPercent(ItemStack stack,
                net.minecraft.world.entity.player.Player player, String aspect) {
            return 5;
        }
        @Override public int baseRunicCharge(ItemStack stack) { return 1; }
    }
}
