# Подключение API 2.0.5 к аддону

## Gradle и зависимость мода

API нужен только для компиляции:

```groovy
repositories {
    ivy {
        url = uri('https://github.com/mifeex/thaumic-reborn-api/releases/download')
        patternLayout { artifact 'v[revision]/[artifact]-[revision](-[classifier]).[ext]' }
        metadataSources { artifact() }
    }
}

dependencies {
    compileOnly fg.deobf('com.thaumicreborn:thaumic-reborn-api:2.0.5')
}
```

Не используйте `implementation`, Shadow, JarJar или распаковку API JAR. В
готовом аддоне не должно быть `com/thaumicreborn/api/**`.

В `META-INF/mods.toml` объявляется обязательная зависимость от основного мода:

```toml
[[dependencies.your_addon]]
modId="thaumic_reborn"
mandatory=true
versionRange="[1.9.3,2)"
ordering="AFTER"
side="BOTH"
```

Отдельный API JAR в папку `mods` не кладут. Java-регистрации выполняйте после
установки bridge основным модом (обычно common setup/enqueueWork). Datapack
определения не требуют Java-регистрации.

## Java

```java
var aspects = com.thaumicreborn.api.ThaumicRebornApi.aspects().all();
var research = com.thaumicreborn.api.ThaumicRebornApi.research().find("basics");
var nodes = com.thaumicreborn.api.ThaumicRebornApi.aura()
        .withinCube(serverLevel, origin, 32);
var wand = com.thaumicreborn.api.ThaumicRebornApi.wands()
        .createWand("greatwood", "gold", true);
```

Результаты API являются снимками: коллекции immutable, а `ItemStack` в DTO
копируются. Изменения мира и знаний делайте только через server-authoritative
методы соответствующего сервиса.

Для собственного фокуса item реализует `FocusItem`, а поведение регистрируется
через `foci().register(...)`. Для прямого подключения block entity к трубам
реализуйте `EssentiaTransport`. Экипировка может реализовать `VisDiscountGear`,
`RevealingGear`, `RunicArmor`, `ThaumicRepairable`; поножи с геометрией пояса,
заходящей в нижнюю часть нагрудника, — `RaisedWaistArmor`; entity, которую не
должна конвертировать taint-система, — `TaintImmune`.

Для постоянного поднятого пояса достаточно реализовать интерфейс без методов:

```java
public final class AddonLeggingsItem extends ArmorItem
        implements com.thaumicreborn.api.equipment.RaisedWaistArmor {
    // Обычная реализация предмета аддона.
}
```

Если геометрия зависит от NBT или другого состояния предмета, переопределите
`boolean hasRaisedWaist(ItemStack stack)`. Контракт common-side: он не импортирует
классы рендера или другие client-only типы. Thaumic Reborn отдельно проверяет,
что предмет надет именно в слот поножей, прежде чем скрыть конфликтующую поясную
геометрию своего нагрудника.

Анимация непрерывного фокуса задаётся в `FocusDefinition`. Старые конструкторы
автоматически используют классическую `FocusAnimation.WAVE`. Для лучевого
фокуса можно явно выбрать компактную позу зарядки:

```java
var definition = new FocusDefinition(
        ResourceLocation.fromNamespaceAndPath("your_addon", "focus_beam"),
        0x44AAFF,
        true,
        0,
        Map.of("aer", 5),
        FocusAnimation.CHARGE
);
ThaumicRebornApi.foci().register(definition, behavior);
```

Доступные варианты соответствуют TC4: `WAVE` и `CHARGE`.

## Пути и основные форматы datapack

Папка `thaumcraft/` сохранена намеренно. Файл исследования, например,
располагается в `data/your_addon/thaumcraft/research/example.json`:

```json
{
  "id": "your_addon:example",
  "category": "basics",
  "icon": "your_addon:example_item",
  "title": "research.your_addon.example.title",
  "subtitle": "research.your_addon.example.subtitle",
  "parents": ["basics"],
  "hidden_parents": [],
  "reveal_when": {"type": "research_completed", "id": "basics"},
  "unlock_when": {"type": "criterion", "id": "your_addon:example_done"},
  "x": 2,
  "y": 1,
  "node_style": {"frame": "round", "special": false},
  "research_cost": [{"id": "cognitio", "amount": 3}],
  "purchase_cost": [],
  "completion_warp": 0,
  "siblings": [],
  "pages": [{"type": "text", "body": "research.your_addon.example.page.1"}]
}
```

Condition types: `always`, `all_of`, `any_of`, `not`, `research_completed`,
`research_revealed`, `scan`, `scan_aspect`, `aspect_known`, `aspect_amount`,
`warp`, `criterion`. Leaf conditions use `id`; `aspect_amount` and `warp` also
accept `minimum`, and `warp` accepts `measure`. Page types: `text`,
`recipe`, `compound_crafting`, `infusion`, `unavailable`. Recipe pages accept
`recipe` or `recipes`; `requires_research` gates an individual page. Infusion
pages also carry their presentation-only `infusion` object.

Research categories live in `thaumcraft/categories`; constructions in
`thaumcraft/constructions`; generic block transport declarations in
`thaumcraft/essentia_transports`. Aspect, scan, crucible, infusion and wand
formats are the formats parsed by the corresponding current reload listeners;
unknown fields should not be used as an extension mechanism.

У rod/cap в wand JSON поле `texture` принимает полный resource location файла,
например `thaumictinkerer:textures/item/wand_rod_ichorcloth_model.png`. Это же
значение доступно аддонам через `WandRod.texture()` и `WandCap.texture()`.

Arcane recipes live in the normal `recipes` folder and use the new namespace:

```json
{
  "type": "thaumic_reborn:arcane_shaped",
  "research": "your_addon:example",
  "vis": {"ordo": 100},
  "pattern": ["X"],
  "key": {"X": {"item": "minecraft:stone"}},
  "result": {"item": "your_addon:example_item"}
}
```


## Focus effect origins (2.0.5)

Wand positioning and casting animation fixes are implemented by the main mod and
apply to addon foci rendered on its wands. Left-arm draining mirrors the right-arm
motion toward the crosshair, including when the player selects a left main arm.
Use the shared wand display transforms: Minecraft already mirrors left-hand
rotations, so do not pre-invert their Y/Z angles or mirror the returned effect
origin again. This correction is supplied by the main mod; the 2.0.5 API
signatures remain unchanged. Do not copy its renderer into an addon.
On the client thread, use `ThaumicRebornClientApi.focusEffects()` for custom effects:

```java
var effects = ThaumicRebornClientApi.focusEffects();
// During world rendering: endpoint is local to the same pose used for your beam.
var start = effects.renderedTip(player, hand, worldPose);
if (start.isPresent()) {
    // Draw your beam from start.get() with worldPose.
}
```

An empty result means no valid first-person sample: skip that frame or use your
own third-person attachment. Never substitute the local player's tip for another
player. The overload without a pose returns world coordinates from the recent
frame, suitable for a client particle launch. Both hands are supported.

For addon projectiles, call `beginProjectileTick(projectile)` before the client
movement tick and `projectileTrail(projectile, particle)` afterwards. Use
`projectileOffset(projectile, partialTick)` in your entity renderer as well.
These helpers only adjust visuals. Keep server spawn positions, collision and
damage authoritative; never send a rendered tip to the server as a trusted hit.
Use these helpers only from client-side code, not a dedicated-server initializer.

Vis network `available`, `consume` and `node().availableVis()` now observe every
filter on a route. A downstream unfiltered relay cannot restore aspects removed
by an upstream Aqua filter. For display colours, use the effective available
supply rather than the root node's unfiltered pool.

## Focus rejection (2.0.5)

Override `FocusBehavior.canCast(context)` for target, permission and other
preconditions. Keep it side-effect-free: the host calls it before charging vis
and during continuous use. Return `FAIL` from `cast` or `false` from `tick`
when an attempt cannot proceed. Continuous animation starts only after the
first successful tick. The host stops failed use and plays `wandfail` at most
once per 10 server ticks per player, shared across hands and focus types.
Do not play a second failure sound in the addon. Existing implementations
remain compatible through the default `canCast` method.

## Third-person casting (2.0.5)

Shared wand rendering animates the actual caster's arm and keeps the item fixed
to its grip. First-person transforms are unchanged. Custom addon item extensions
can return `ThaumicRebornClientApi.focusEffects().thirdPersonArmPose(entity, hand, stack).orElse(null)`
from `getArmPose`. Use the rendered entity, not `Minecraft.player`. Do not also
apply a first-person orbit to the held item in third person.
