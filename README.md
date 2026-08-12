# Thaumic Reborn API

Compile-time API for addons targeting Thaumic Reborn on Minecraft Forge
1.20.1. This project is not a separately installed mod.

Addon developers use the published artifact as `compileOnly`. The same API
classes are built into the main Thaumic Reborn JAR, so a player installs only:

- `thaumic-reborn-<version>.jar`;
- the addon JAR.

Do not shade, jar-in-jar, or otherwise copy this API artifact into an addon.
The addon's `mods.toml` dependency must target the compatibility mod ID
`thaumcraftmodern`.

Datapack definitions supplied by addons are discovered across namespaces:

- `data/<addonid>/thaumcraft/aspects`;
- `data/<addonid>/thaumcraft/research`;
- `data/<addonid>/thaumcraft/scans`;
- `data/<addonid>/thaumcraft/crucible_recipes`;
- `data/<addonid>/thaumcraft/infusion_recipes`;
- `data/<addonid>/thaumcraft/wands`.

Arcane recipes use `data/<addonid>/recipes` with type
`thaumcraftmodern:arcane_shaped` or `thaumcraftmodern:arcane_shapeless`.

The Java API is intended for runtime queries, server-authoritative player
knowledge changes, wand creation, and behavior that cannot be expressed as
data.

## API 1.1

- Implement `com.thaumicreborn.api.essentia.EssentiaTransport` on an addon
  block entity to connect it directly to the essentia network.
- `ThaumicRebornApi.aura()` exposes immutable node snapshots, one-time node
  initialization, and revision-checked server mutation.
- Implement `FocusItem`, then register its `FocusDefinition` and
  `FocusBehavior` through `ThaumicRebornApi.foci()` during common setup. The
  wand, focus wheel, vis payment, continuous casting, and cooldown paths all
  recognize it.
- Armor, weapons, tools, and accessories can opt into `VisDiscountGear`,
  `RevealingGear`, `RunicArmor`, and `ThaumicRepairable`. Registration remains
  normal Forge `DeferredRegister` registration.
- Client code can use `ThaumicRebornClientApi.aspects()` for the original
  aspect renderer and `ThaumicRebornClientApi.hud()` to add addon containers
  to the shared revealing-gear HUD.

Everything under `com.thaumicreborn.api.client` is client-only. The
`example-addon` demonstrates a custom focus, weapon, revealing/runic helmet,
and all three thaumic recipe formats while still bundling no API classes.
