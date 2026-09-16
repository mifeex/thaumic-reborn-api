package com.thaumicreborn.api.focus;

/**
 * Classic TC4 pose used while a continuous wand focus is active.
 *
 * <p>{@link #WAVE} is the default: a broad, slow casting motion. Use
 * {@link #CHARGE} for focused beams and other effects that need the small,
 * rapid TC4 charging vibration.</p>
 */
public enum FocusAnimation {
    WAVE,
    CHARGE
}
