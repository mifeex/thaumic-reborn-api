package com.thaumicreborn.api.essentia;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

/**
 * Side-aware TC4 essentia transport contract for addon block entities.
 * Implementations are discovered directly by Thaumic Reborn's tube network.
 */
public interface EssentiaTransport {
    boolean isConnectable(Direction side);
    boolean canInputFrom(Direction side);
    boolean canOutputTo(Direction side);
    void setSuction(@Nullable String aspect, int amount);
    @Nullable String suctionType(Direction side);
    int suctionAmount(Direction side);
    @Nullable String essentiaType(Direction side);
    int essentiaAmount(Direction side);
    int minimumSuction();
    int takeEssentia(String aspect, int amount, Direction side);
    int addEssentia(String aspect, int amount, Direction side);

    default boolean canReturnEssentia() { return true; }
    default EssentiaFlowMode suctionFlowMode(Direction side) {
        return EssentiaFlowMode.SUPPLY;
    }
    default long suctionController(Direction side) { return 0L; }
    default boolean renderExtendedTube() { return false; }
}
