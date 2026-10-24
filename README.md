# Thaumic Reborn API

Compile-time API for addons targeting Thaumic Reborn on Minecraft 1.21.1 / NeoForge.
Version 2.0.7-neoforge follows the current `thaumic_reborn` mod and resource namespace.
This artifact is a separately installed NeoForge mod (`thaumic_reborn_api`).

Addon developers must use the API as `compileOnly`. The public API JAR supplies these classes at runtime. Players must install
both the main mod and the API JAR, plus their addons. NeoForge rejects the main
mod at startup when the API is missing. Never shade, JarJar, unpack, or otherwise copy the API classes into
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

The NeoForge port uses the `arcana/` folder beneath each namespace, matching
the migrated main-mod data. The main-mod loaders must consume:

- `data/<addonid>/arcana/aspects/*.json`
- `data/<addonid>/arcana/categories/*.json`
- `data/<addonid>/arcana/research/*.json`
- `data/<addonid>/arcana/scans/*.json`
- `data/<addonid>/arcana/crucible_recipes/*.json`
- `data/<addonid>/arcana/infusion_recipes/*.json`
- `data/<addonid>/arcana/wands/*.json`
- `data/<addonid>/arcana/constructions/*.json`
- `data/<addonid>/arcana/essentia_transports/*.json`

Ordinary recipe-manager files remain in `data/<addonid>/recipe/*.json`.
Thaumic recipe serializer IDs are `thaumic_reborn:arcane_shaped`,
`thaumic_reborn:arcane_shapeless`, `thaumic_reborn:arcane_wand_assembly`,
`thaumic_reborn:arcane_sceptre_assembly`, `thaumic_reborn:double_smelting`,
`thaumic_reborn:double_blasting`, and `thaumic_reborn:knowledge_fragment_research`.

See [ADDON_SETUP.md](ADDON_SETUP.md) for Gradle, `neoforge.mods.toml`, lifecycle and JSON
examples.

## Compatibility

2.0.7-neoforge exposes each wand rod and cap's full texture `ResourceLocation`. The
Legacy constructors remain available and derive the built-in conventional
`thaumic_reborn:textures/item/..._model.png` path.

The 2.0 API introduced breaking changes. It changes `MOD_ID`, replaces the abbreviated research
DTO with `ResearchDefinition`, and adds mandatory methods to `ApiServices` and
several service interfaces. The corresponding main-mod bridge must be updated
before the main mod embeds this API version.

## Building the NeoForge API

Run `./gradlew build --no-daemon --max-workers=1 --console=plain`.
Gradle provisions Java 21 through the configured toolchain resolver.
The artifact is `build/libs/thaumic-reborn-api-neoforge-2.0.7-neoforge.jar`.
The NeoForge artifact has separate Maven coordinates from the Forge API.
This does not make Forge 1.20.1 addon binaries compatible: rebuild and port addons.
The API is a standalone NeoForge mod with identity `thaumic_reborn_api`.
The main mod bundles this complete JAR through Jar-in-Jar and binds its
ApiServices/ClientApiServices implementations. Do not copy duplicate API classes
into the main mod or an addon. Development runs must load the API as a mod too.

## Additions in 2.0.7-neoforge

`FocusApi.registerUpgrade` registers immutable namespaced custom upgrades with
`legacyId=-1`. The host protects native names and rejects duplicates. Custom
upgrade keys are saved independently of transient menu ordinals.
`FocusBehavior.paysOwnVis()` defaults to false, preserving automatic payment for
old addons. Override it only when a behavior checks permissions and atomically
pays its dynamic cost using `FocusApi.consumeVis` before committing effects.
The host applies Frugal discounts to addon costs. The release also exposes
native essentia-crystal creation and Infernal Furnace/ore-cluster helpers.
