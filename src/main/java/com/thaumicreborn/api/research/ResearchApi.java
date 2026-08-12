package com.thaumicreborn.api.research;

import java.util.List;
import java.util.Optional;

/** Read-only access to research definitions loaded from all namespaces. */
public interface ResearchApi {
    Optional<Research> find(String id);

    List<Research> all();
}
