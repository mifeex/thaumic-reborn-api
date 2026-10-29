package com.thaumicreborn.api.world;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/** Immutable rule metadata; callbacks must be deterministic, non-null and side-effect-free. */
public record TaintBlockConversion(ResourceLocation id, Predicate<BlockState> matches,
                                    Function<BlockState, BlockState> result) {
    public TaintBlockConversion {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(matches, "matches");
        Objects.requireNonNull(result, "result");
    }

    /** Convert every state of one block to the supplied target state. */
    public TaintBlockConversion(ResourceLocation id, Block original, BlockState tainted) {
        this(id, matchesBlock(original), constantResult(tainted));
    }

    private static Predicate<BlockState> matchesBlock(Block block) {
        Objects.requireNonNull(block, "original");
        return state -> state.is(block);
    }

    private static Function<BlockState, BlockState> constantResult(BlockState result) {
        Objects.requireNonNull(result, "tainted");
        return state -> result;
    }
}
