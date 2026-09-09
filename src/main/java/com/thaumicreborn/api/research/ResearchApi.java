package com.thaumicreborn.api.research;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import java.util.List;
import java.util.Optional;

/** Definition catalog plus the server-authoritative research workflow. */
public interface ResearchApi {
    Optional<ResearchDefinition> find(String id);
    List<ResearchDefinition> all();
    long revision();
    Optional<ResearchCategory> category(String id);
    List<ResearchCategory> categories();
    long categoryRevision();
    boolean isVisible(Player player, String researchId);
    boolean isAvailable(Player player, String researchId);
    boolean canCreateNotes(Player player, String researchId);
    ResearchProgressUpdate reconcile(ServerPlayer player);
    ResearchPurchaseResult purchase(ServerPlayer player, String researchId);
}
