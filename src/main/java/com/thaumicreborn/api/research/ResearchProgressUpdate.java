package com.thaumicreborn.api.research;
import java.util.List;
public record ResearchProgressUpdate(List<String> revealed, List<String> autoCompleted) {
    public ResearchProgressUpdate { revealed = List.copyOf(revealed); autoCompleted = List.copyOf(autoCompleted); }
    public boolean changed() { return !revealed.isEmpty() || !autoCompleted.isEmpty(); }
}
