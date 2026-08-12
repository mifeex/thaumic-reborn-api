# Подключение к аддону

API используется только при компиляции:

```groovy
repositories {
    maven { url = uri("https://your.maven.example/releases") }
}

dependencies {
    compileOnly fg.deobf(
            "com.thaumicreborn:thaumic-reborn-api:1.1.0"
    )
}
```

Не используйте `implementation`, Shadow, JarJar или распаковку API JAR.
В собранном аддоне не должно быть файлов внутри
`com/thaumicreborn/api/`.

В `mods.toml` нужна зависимость от основного мода:

```toml
[[dependencies.your_addon]]
modId="thaumcraftmodern"
mandatory=true
versionRange="[1.6.8,2)"
ordering="AFTER"
side="BOTH"
```

Во время игры классы `com.thaumicreborn.api.*` предоставляет
`thaumic-reborn-<version>.jar`. Отдельный API JAR в папку `mods` класть не
нужно.

## Пример вызова

```java
import com.thaumicreborn.api.ThaumicRebornApi;

var aspects = ThaumicRebornApi.aspects().all();
var research = ThaumicRebornApi.research().find("basics");
var wand = ThaumicRebornApi.wands()
        .createWand("greatwood", "gold", true);
```

Аспекты, исследования, сканы и thaumic-рецепты аддон добавляет ресурсами в
своём namespace, например
`data/your_addon/thaumcraft/research/example.json`. Регистраторы основного
мода читают определения из всех загруженных namespaces.
