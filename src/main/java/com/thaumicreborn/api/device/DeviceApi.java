package com.thaumicreborn.api.device;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import java.util.Optional;
/** Common immutable inspection layer for addon-relevant thaumic devices. */
public interface DeviceApi {
    Optional<DeviceState> state(Level level, BlockPos position);
}
