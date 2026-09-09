package com.thaumicreborn.api.construction;
import java.util.List;
import java.util.Optional;
/** Read-only catalog of datapack-defined multiblock construction handlers. */
public interface ConstructionApi {
    Optional<ConstructionDefinition> find(String id);
    List<ConstructionDefinition> all();
}
