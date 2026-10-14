package com.thaumicreborn.api.focus;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import java.util.Objects;

/** Shared base for built-in and addon foci. Register definition() with the casting behavior. */
public abstract class AbstractFocusItem extends Item implements FocusItem {
    protected final FocusDefinition definition;
    private final FocusDecoration decoration;

    protected AbstractFocusItem(Properties properties, FocusDefinition definition) {
        this(properties, definition, FocusDecoration.DEFAULT);
    }

    protected AbstractFocusItem(Properties properties, FocusDefinition definition,
                                FocusDecoration decoration) {
        super(properties.stacksTo(1));
        this.definition = Objects.requireNonNull(definition, "definition");
        this.decoration = Objects.requireNonNull(decoration, "decoration");
    }

    public final FocusDefinition definition() { return definition; }
    @Override public final ResourceLocation focusId() { return definition.id(); }
    @Override public FocusDecoration decoration() { return decoration; }
}
