# Подключение API 2.0 к аддону

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
    compileOnly fg.deobf('com.thaumicreborn:thaumic-reborn-api:2.0.0')
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
