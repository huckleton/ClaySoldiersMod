/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.entity.soldier;

import de.sanandrew.mods.claysoldiers.api.entity.IDisruptable;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.ISoldierEffect;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.ISoldierEffectInst;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.IHandedUpgradeable;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgradeInst;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public interface ISoldier<T extends EntityCreature & ISoldier<T>>
        extends IDisruptable, IHandedUpgradeable
{
    void expireEffect(ISoldierEffect effect);

    ISoldierEffectInst addEffect(ISoldierEffect effect, int duration);

    boolean hasEffect(String effectId);

    boolean hasEffect(ISoldierEffect effect);

    int getEffectDurationLeft(ISoldierEffect effect);

    int getEffectDurationLeft(String effectId);

    ISoldierEffectInst getEffectInstance(String effectId);

    ISoldierEffectInst getEffectInstance(ISoldierEffect entry);

    void removeTask(EntityAIBase task);

    void setBreathableUnderwater(boolean breathable);

    ITeam getSoldierTeam();

    T getEntity();

    int getTextureType();

    int getTextureId();

    void setNormalTextureId(byte id);

    void setRareTextureId(byte id);

    void setUniqueTextureId(byte id);

    boolean hasBehaviorUpgrade();

    void destroyUpgrade(ISoldierUpgrade upgrade, EnumUpgradeType type, boolean silent);

    ISoldierUpgradeInst addUpgrade(ISoldierUpgrade upgrade, @Nonnull ItemStack stack);

    ISoldierUpgradeInst addUpgrade(ISoldierUpgrade upgrade, EnumUpgradeType type, @Nonnull ItemStack stack);

    ISoldierUpgradeInst getUpgradeInstance(String upgradeId, EnumUpgradeType type);

    boolean canPickupUpgrade(ISoldierUpgrade upgrade, ItemStack stack);

    long countUpgradesOfType(EnumUpgradeType type);

    ISoldierUpgradeInst getUpgradeInstance(ISoldierUpgrade upgrade, EnumUpgradeType type);

    boolean hasUpgrade(String id, EnumUpgradeType type);

    boolean hasUpgrade(ISoldierUpgrade upgrade, EnumUpgradeType type);

    boolean isEnemyValid(EntityLivingBase entity);

    double getChasingPosX(float partTicks);

    double getChasingPosY(float partTicks);

    double getChasingPosZ(float partTicks);

    List<ISoldierUpgradeInst> getUpgradeInstanceList();
}
