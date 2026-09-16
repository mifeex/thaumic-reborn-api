package com.thaumicreborn.api.vis;
/** Implement on a block entity and register its loaded position with VisNetworkApi.
 * Values are centivis available this tick; consume must debit its shared budget.
 * Implementations must never replenish a budget from a query. */
public interface VisSource {
    boolean visSourceActive();
    int availableCentivis(String primalAspect);
    int consumeCentivis(String primalAspect, int maximum);
}
