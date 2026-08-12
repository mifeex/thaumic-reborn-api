package com.thaumicreborn.api.knowledge;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Set;

/** Server-authoritative player knowledge facade. */
public interface KnowledgeApi {
    ResearchStatus researchStatus(Player player, String researchId);

    boolean knowsResearch(Player player, String researchId);

    boolean hasCompletedResearch(Player player, String researchId);

    Set<String> knownAspects(Player player);

    int aspectAmount(Player player, String aspectId);

    Set<String> completedScans(Player player);

    int warp(Player player, WarpType type);

    boolean revealResearch(ServerPlayer player, String researchId);

    boolean completeResearch(ServerPlayer player, String researchId);

    boolean addAspectPoints(ServerPlayer player, String aspectId, int amount);

    int addWarp(ServerPlayer player, WarpType type, int amount);
}
