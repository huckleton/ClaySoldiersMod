package de.sanandrew.mods.claysoldiers.api.entity.soldier.effect;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.ISoldierEffect;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public interface IEffectRegistry
{
    boolean registerEffect(String id, ISoldierEffect effect);

    @Nullable
    ISoldierEffect getEffect(String id);

    @Nullable
    String getId(ISoldierEffect effect);

    List<ISoldierEffect> getEffects();
}
