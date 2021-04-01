/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.effect;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.IEffectRegistry;

public final class Effects
{
    public static final String TIME_BOMB = "effect_timeBomb";
    public static final String BLINDING_REDSTONE = "effect_blind";
    public static final String STICKING_SLIMEBALL = "effect_sticky";
    public static final String MOVE_BACK = "effect_moveBack";

    public static void initialize(IEffectRegistry registry) {
        registry.registerEffect(TIME_BOMB, EffectTimeBomb.INSTANCE);
        registry.registerEffect(BLINDING_REDSTONE, EffectRedstone.INSTANCE);
        registry.registerEffect(STICKING_SLIMEBALL, EffectSlimeball.INSTANCE);
        registry.registerEffect(MOVE_BACK, EffectBackWalk.INSTANCE);
    }
}
