package com.thaumicreborn.api.knowledge;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Set;
import java.util.Map;
import java.util.Optional;

/** Server-authoritative player knowledge facade. */
public interface KnowledgeApi {
    ResearchStatus researchStatus(Player player, String researchId);

    boolean knowsResearch(Player player, String researchId);

    boolean hasCompletedResearch(Player player, String researchId);

    Set<String> knownAspects(Player player);

    int aspectAmount(Player player, String aspectId);

    Set<String> completedScans(Player player);

    int warp(Player player, WarpType type);

    Optional<KnowledgeSnapshot> snapshot(Player player);

    boolean hasCriterion(Player player, String criterionId);

    int warpCounter(Player player);

    int runicCharge(Player player);

    boolean revealResearch(ServerPlayer player, String researchId);

    boolean completeResearch(ServerPlayer player, String researchId);

    boolean addAspectPoints(ServerPlayer player, String aspectId, int amount);

    boolean consumeAspectPoints(ServerPlayer player, Map<String, Integer> costs);

    boolean recordCriterion(ServerPlayer player, String criterionId, String reason);

    int addWarp(ServerPlayer player, WarpType type, int amount);

    int setWarp(ServerPlayer player, WarpType type, int amount);

    int setWarpCounter(ServerPlayer player, int amount);

    int setRunicCharge(ServerPlayer player, int amount);
}
