# Thaumic Reborn API

Compile-time API for addons targeting Thaumic Reborn on Minecraft Forge 1.20.1.
Version 2.0.9 follows the current `thaumic_reborn` mod and resource namespace.
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
- aura-node creation, snapshots, spatial queries and revision-aware mutations;
- tainted mobs, infection/conversion, passive spread sources and block conversion rules;
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

## Extending taint (2.0.9)

Use `ThaumicRebornApi.taint()` (also available as `world().taint()`). The API
integrates addon content with the main mod's live ecology and Flux Taint deaths.
Entity types, attributes, renderers, spawn eggs and natural-spawn rules are still
registered by the addon through Forge. Register taint metadata once during
`FMLCommonSetupEvent.enqueueWork`, on both physical sides, before loading worlds:

```java
event.enqueueWork(() -> {
    TaintApi taint = ThaumicRebornApi.taint();
    ResourceLocation taintedFox = ResourceLocation.fromNamespaceAndPath("example", "tainted_fox");

    // Existing Forge entity type: infect ground every 20 ticks, also spread its
    // biome, and transmit 160 ticks of Flux Taint on damaging attacks.
    taint.registerMob(new TaintMobDefinition(taintedFox, 20, true, 160));
    taint.registerMobConversion(
        ResourceLocation.fromNamespaceAndPath("minecraft", "fox"), taintedFox);

    taint.registerSpreadSource(
        ResourceLocation.fromNamespaceAndPath("example", "taint_seed"));
    taint.registerBlockConversion(new TaintBlockConversion(
        ResourceLocation.fromNamespaceAndPath("example", "soil_infection"),
        ExampleBlocks.CLEAN_SOIL.get(), ExampleBlocks.TAINTED_SOIL.get().defaultBlockState()));
    taint.registerSpreadSource(
        ResourceLocation.fromNamespaceAndPath("example", "tainted_soil"));
});
```

Mob definitions make the entity tainted for immunity and conversion checks;
they do not change its AI or visuals. Zero terrain interval disables automatic
ground infection; zero attack duration disables attack infection. Terrain actions
respect `mobGriefing` and Forge's mob-griefing event. Registered conversion targets
are also used by the normal infected-death handler; `TaintImmune`, players and
already tainted mobs cannot be converted. An explicit `convert(victim)` does not
require an existing infection effect and removes the victim only after a
replacement has been inserted successfully. Names are preserved; arbitrary addon
NBT is not copied. Failed death replacements leave ordinary death handling intact.

Passive source blocks are sampled in vanilla's ticking chunks at `randomTickSpeed`,
even if their block class does not opt into random ticks. They can seed taint
outside Tainted Lands, count as taint neighbours for native ecology, and are not
decayed by the addon spread operation. `randomTickSpeed=0` disables this automatic
block sampling. `taintSpreadRate=0` disables the biome part of addon spread, matching
the main mod's setting; block infection is separate. No unloaded chunks are loaded.
Sources and conversions are independent: register a conversion result as a source
as well if it should continue spreading.

Custom block rules run before native fallback, ordered by ascending rule ID,
independent of registration order. Duplicate IDs are rejected. For tags or state
properties use the predicate/function constructor, for example:

```java
new TaintBlockConversion(ruleId,
    state -> state.is(mySoilTag),
    state -> ExampleBlocks.TAINTED_SOIL.get().defaultBlockState());
```

Callbacks must be deterministic, side-effect-free, and return non-null states.
A matching rule returning the original state intentionally prevents native fallback.
Rules apply to both native ecology random ticks and explicit API block infection.

At runtime, call these operations on the server thread:

```java
taint.infect(victim, 160, 0);                   // Flux Taint, duration/amplifier
taint.infectBlock(level, groundPosition);        // One deterministic conversion
taint.spreadFrom(level, sourcePosition);         // One bounded random spread attempt
taint.spawn(level, spawnPosition, taintedFox);   // Optional<Mob>
taint.convert(victim);                          // Optional<Mob>, immediate conversion
```

`infect` rejects immune, tainted and undead targets. `spawn` accepts registered or
native tainted mobs and checks placement collisions. World operations reject
unloaded positions and positions outside the build limits/world border. Direct
block operations are intended for addon-controlled use; when calling from custom
mob logic, the addon must check mob-griefing permission as the automatic path does.

Use the matching rebuilt main mod with API 2.0.9. Older providers return an
unsupported taint service: registrations throw `UnsupportedOperationException`,
and mutations decline with `false`/`Optional.empty()`. Keep the API `compileOnly`;
addons must not bundle their own copy.

## Creating aura nodes (2.0.8)

Call on the server thread with a `ServerLevel` and an absolute `BlockPos`:

```java
boolean created = ThaumicRebornApi.aura().create(
    level, position, AuraNodeType.PURE, AuraNodeModifier.BRIGHT,
    Map.of("aer", 40, "ordo", 25));
```

This places a native aura-node block, gives it a fresh UUID and fills its current
and maximum aspect pools equally. Primal and compound aspect IDs are supported.
To specify partially depleted pools or an existing identity, use
`create(level, position, new AuraNode(id, type, modifier, current, maximum, revision))`.
Both maps must have the same non-empty set of lowercase aspect IDs and non-negative
amounts, current must not exceed maximum, and revision must be non-negative.
Invalid state throws `IllegalArgumentException` before any block is placed.

Creation returns `false` for occupied positions, unloaded chunks, positions
outside the build limits/world border, or older providers without this feature.
It does not load chunks or overwrite blocks. The matching main-mod bridge handles
saving, client synchronization and spatial queries. `initialize(...)` remains
available for an already placed, uninitialized node block.

Use API 2.0.8 with a matching rebuilt provider; the API alone does not implement
world placement. This creates instances of the existing node types, not new
registered node types or renderers. Keep the API `compileOnly` in addons.

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
