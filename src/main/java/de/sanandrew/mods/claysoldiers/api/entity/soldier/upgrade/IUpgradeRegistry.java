/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgrade;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public interface IUpgradeRegistry
{
    boolean registerUpgrade(String id, ISoldierUpgrade upgrade);

    @Nullable
    ISoldierUpgrade getUpgrade(String id);

    @Nullable
    ISoldierUpgrade getUpgrade(ItemStack stack);

    @Nullable
    String getId(ISoldierUpgrade upgrade);

    List<ISoldierUpgrade> getUpgrades();
}
