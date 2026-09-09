package com.thaumicreborn.api.link;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import java.util.UUID;
import java.util.Optional;
/** Stable mirror-link and node-jar operations. */
public interface LinkApi {
    Optional<MirrorLink> mirror(ServerLevel level, BlockPos position);
    boolean linkMirrors(ServerLevel firstLevel, BlockPos first, ServerLevel secondLevel, BlockPos second);
    Optional<NodeJarPayload> nodeJar(ItemStack stack);
    ItemStack createNodeJar(NodeJarPayload payload);
    NodeJarResult captureNode(ServerPlayer player, InteractionHand hand, UUID operationId,
                              BlockPos nodePosition, UUID expectedNodeId);
    NodeJarResult placeNode(ServerPlayer player, InteractionHand hand, UUID operationId,
                            BlockPos position);
    record NodeJarResult(NodeJarStatus status, NodeJarPayload payload) {
        public Optional<NodeJarPayload> payloadOptional() { return Optional.ofNullable(payload); }
    }
    enum NodeJarStatus {
        CAPTURED, PLACED, DUPLICATE_OPERATION, DUPLICATE_PAYLOAD, NOT_SERVER,
        RESEARCH_REQUIRED, WRONG_DIMENSION, NODE_NOT_LOADED, CHUNK_NOT_LOADED,
        TARGET_BLOCKED, TOO_FAR, CASTING_TOOL_REQUIRED, INSUFFICIENT_VIS,
        INVALID_STRUCTURE, NODE_CHANGED, STACK_CHANGED, WORLD_TRANSACTION_FAILED,
        EMPTY_JAR, INVALID_PAYLOAD
    }
}
