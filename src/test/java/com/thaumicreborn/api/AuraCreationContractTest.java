package com.thaumicreborn.api;

import com.thaumicreborn.api.aura.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class AuraCreationContractTest {
    @Test
    void convenienceOverloadDelegatesWithFreshIdentityAndImmutableFullPools() {
        var captured = new AtomicReference<AuraNode>();
        AuraApi api = new Adapter() {
            @Override public boolean create(ServerLevel level, BlockPos position, AuraNode node) {
                captured.set(node);
                return true;
            }
        };
        var aspects = new HashMap<>(Map.of("aer", 40, "praecantatio", 12));
        assertTrue(api.create(null, BlockPos.ZERO, AuraNodeType.PURE, AuraNodeModifier.BRIGHT, aspects));
        var first = captured.get();
        aspects.put("aer", 99);
        assertEquals(Map.of("aer", 40, "praecantatio", 12), first.current());
        assertEquals(first.current(), first.maximum());
        assertEquals(AuraNodeType.PURE, first.type());
        assertEquals(AuraNodeModifier.BRIGHT, first.modifier());
        assertEquals(0, first.revision());
        assertTrue(api.create(null, BlockPos.ZERO, AuraNodeType.NORMAL, AuraNodeModifier.NORMAL, aspects));
        assertNotEquals(first.id(), captured.get().id());
    }

    @Test
    void oldProvidersCanDeclineCreationWithoutImplementingANewMethod() throws Exception {
        AuraApi api = new Adapter();
        assertTrue(AuraApi.class.getMethod("create", ServerLevel.class,
                BlockPos.class, AuraNode.class).isDefault());
        assertFalse(api.create(null, BlockPos.ZERO, AuraNodeType.NORMAL,
                AuraNodeModifier.NORMAL, Map.of("aer", 1)));
    }
    private static class Adapter implements AuraApi {
        public java.util.Optional<AuraNode> node(net.minecraft.world.level.Level l, BlockPos p) { return java.util.Optional.empty(); }
        public java.util.List<LocatedAuraNode> withinCube(ServerLevel l, BlockPos p, int r) { return java.util.List.of(); }
        public java.util.Optional<LocatedAuraNode> nearest(ServerLevel l, BlockPos p, int r,
                java.util.function.Predicate<AuraNode> f) { return java.util.Optional.empty(); }
        public boolean initialize(ServerLevel l, BlockPos p, AuraNode n) { return false; }
        public boolean replaceAspects(ServerLevel l, BlockPos p, long r, Map<String, Integer> c,
                Map<String, Integer> m) { return false; }
        public int drain(ServerLevel l, BlockPos p, String a, int m) { return 0; }
        public boolean remove(ServerLevel l, BlockPos p, long r) { return false; }
    }

}
