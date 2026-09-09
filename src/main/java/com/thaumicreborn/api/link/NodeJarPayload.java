package com.thaumicreborn.api.link;
import com.thaumicreborn.api.aura.AuraNode;
import java.util.Objects;
import java.util.UUID;
public record NodeJarPayload(UUID payloadId, Origin origin, AuraNode node) {
    public NodeJarPayload { Objects.requireNonNull(payloadId, "payloadId"); Objects.requireNonNull(origin, "origin"); Objects.requireNonNull(node, "node"); }
    public enum Origin { SURVIVAL, CREATIVE_TEMPLATE }
}
