/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.behavior;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.IHandedUpgradeable;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.UpgradeFunctions;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Objects;

@UpgradeFunctions({EnumUpgFunctions.ON_DEATH, EnumUpgFunctions.ON_TICK})
public class UpgradeStandardBehavior
        implements ISoldierUpgrade
{
    private final ItemStack[] upgItems;
    private String shortName;

    @Override
    public String getModId() {
        return CsmConstants.ID;
    }

    @Override
    public String getShortName() {
        return this.shortName;
    }

    public UpgradeStandardBehavior(String shortName, ItemStack... items) {
        this.upgItems = items;
        this.shortName = shortName;
        this.shortName = this.shortName.substring(this.shortName.indexOf('.') + 1);
    }

    @Override
    @Nonnull
    public ItemStack[] getStacks() {
        return this.upgItems;
    }

    @Nonnull
    @Override
    public EnumUpgradeType getType(IHandedUpgradeable checker) {
        return EnumUpgradeType.BEHAVIOR;
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        drops.add(upgradeInst.getSavedStack());
    }

    @Override
    public void onTick(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst) {
        EntityCreature soldierL = soldier.getEntity();
        if( soldierL.world.isRemote && MiscUtils.RNG.randomInt(50) == 0 ) {
            ItemStack stack = upgradeInst.getSavedStack();
            /*ClaySoldiersMod.proxy.spawnParticle(EnumParticle.ITEM_BREAK, soldierL.world.provider.getDimension(), soldierL.posX, soldierL.posY + soldierL.getEyeHeight(),
            soldierL.posZ, Item.getIdFromItem(stack.getItem()), stack.getItemDamage(),
            stack.hasTagCompound() ? Objects.requireNonNull(stack.getTagCompound()).toString() : "");*/
        }
    }
}
