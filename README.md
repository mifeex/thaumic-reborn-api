# Thaumic Reborn API

Compile-time API for addons targeting Thaumic Reborn on Minecraft Forge 1.20.1.
Version 2.0.5 follows the current `thaumic_reborn` mod and resource namespace.
This artifact is a Java library, not a separately installed Forge mod.

Addon developers must use the API as `compileOnly`. The main Thaumic Reborn JAR
supplies these exact classes at runtime, so players install only the main mod and
their addons. Never shade, JarJar, unpack, or otherwise copy the API classes into
an addon.

## Surface

`ThaumicRebornApi` exposes immutable definitions and narrow services for:

- aspects, scans, arcane/crucible/infusion recipes and essentia transport;
- complete research definitions/categories/conditions/pages and authoritative
  reveal, availability, note, reconcile and purchase operations;
- player aspect knowledge, criteria, research state, warp counters and runic state;
- wand components/vis, focus behavior, five-rank upgrades and 18-slot pouches;
- aura-node snapshots, spatial queries and revision-aware mutations;
- vis-network routes, attunements, availability and machine consumption;
- golem configuration, constructions, mirrors, node jars, taint/biomes/Outer Lands,
  item decomposition and stable alchemical/device snapshots;
- equipment extension contracts, including stack-aware raised-waist armor
  compatibility, and client-only aspect/HUD integration.

Everything under `com.thaumicreborn.api.client` is client-only. Common or
dedicated-server code must not load those classes.

Addon leggings whose model rises into the lower chest-armor region can implement
`com.thaumicreborn.api.equipment.RaisedWaistArmor`. Its default method makes the
interface a simple marker; override `hasRaisedWaist(ItemStack)` only when the
answer depends on that stack's state. The contract is common-side and contains
no renderer or other client-only types.

## Datapacks

The current reload listeners intentionally retain the historical `thaumcraft/`
folder beneath each namespace. Addon definitions are loaded from:

- `data/<addonid>/thaumcraft/aspects/*.json`
- `data/<addonid>/thaumcraft/categories/*.json`
- `data/<addonid>/thaumcraft/research/*.json`
- `data/<addonid>/thaumcraft/scans/*.json`
- `data/<addonid>/thaumcraft/crucible_recipes/*.json`
- `data/<addonid>/thaumcraft/infusion_recipes/*.json`
- `data/<addonid>/thaumcraft/wands/*.json`
- `data/<addonid>/thaumcraft/constructions/*.json`
- `data/<addonid>/thaumcraft/essentia_transports/*.json`

Ordinary recipe-manager files remain in `data/<addonid>/recipes/*.json`.
Thaumic recipe serializer IDs are `thaumic_reborn:arcane_shaped`,
`thaumic_reborn:arcane_shapeless`, `thaumic_reborn:arcane_wand_assembly`,
`thaumic_reborn:arcane_sceptre_assembly`, `thaumic_reborn:double_smelting`,
`thaumic_reborn:double_blasting`, and `thaumic_reborn:knowledge_fragment`.

See [ADDON_SETUP.md](ADDON_SETUP.md) for Gradle, `mods.toml`, lifecycle and JSON
examples.

## Compatibility

2.0.5 exposes each wand rod and cap's full texture `ResourceLocation`. The
Legacy constructors remain available and derive the built-in conventional
`thaumic_reborn:textures/item/..._model.png` path.

The 2.0 API introduced breaking changes. It changes `MOD_ID`, replaces the abbreviated research
DTO with `ResearchDefinition`, and adds mandatory methods to `ApiServices` and
several service interfaces. The corresponding main-mod bridge must be updated
before the main mod embeds this API version.

## Custom focus upgrades (2.0.7)

During common setup, before worlds/menus load, register the same immutable
metadata on client and server:

```java
ResourceLocation slow = ResourceLocation.fromNamespaceAndPath("example", "slow");
ThaumicRebornApi.foci().registerUpgrade(new FocusUpgradeDefinition(
    slow, -1,
    ResourceLocation.fromNamespaceAndPath("example", "textures/foci/slow.png"),
    "focus.example.slow.name", "focus.example.slow.description",
    Map.of("vinculum", 8)));
```

Declare this ID in the focus's `upgradesByRank`, then read its level from
`foci().state(focus).levels()`. The provider supplies the native manipulator UI,
rank validation, XP and primal network payment. Addons implement the actual
custom effect. Custom identities are namespaced, with `legacyId=-1`; duplicate
IDs and the provider namespace are reserved/rejected. Numeric native upgrade NBT
remains compatible, while custom/active-process saves use stable namespaced keys.

`AspectApi.createEssentiaCrystal`, `AlchemyApi.nativeCluster` and
`AlchemyApi.smeltingBonus` return native stacks without requiring provider NBT.
Empty means unsupported. `FocusBehavior.paysOwnVis()` defaults false; override it
only for a single cast that pays a dynamic cost with `foci().consumeVis` after
permission checks and before its effects. The provider then skips its automatic
single-cast debit; Frugal still applies inside `consumeVis`.

Use the rebuilt matching provider: older providers may expose default unsupported
or empty implementations for the newly added optional methods. Addons must not
bundle a second copy of the API.
