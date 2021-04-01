/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.model.item;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

abstract class MeshDefUUID<T>
        implements MeshDef
{
    public final Map<String, ModelResourceLocation> modelRes = new HashMap<>();

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack) {
        T type = getType(stack);
        ResourceLocation regName = stack.getItem().getRegistryName();
        return type != null ? this.modelRes.get(getId(type)) : regName != null ? new ModelResourceLocation(regName, "inventory") : null;
    }

    public abstract T getType(ItemStack stack);
    public abstract String getId(T type);

    public ResourceLocation[] getResLocations() {
        return this.modelRes.values().toArray(new ModelResourceLocation[0]);
    }

    static final class Soldier
            extends MeshDefUUID<ITeam>
    {
        Soldier() {
            for( ITeam info : TeamRegistry.INSTANCE.getTeams() ) {
                ModelResourceLocation modelRes = new ModelResourceLocation(info.getItemModel(), "inventory");
                this.modelRes.put(info.getId(), modelRes);
            }
        }

        @Override
        public ITeam getType(ItemStack stack) { return TeamRegistry.INSTANCE.getTeam(stack); }

        @Override
        public String getId(ITeam type) { return type.getId(); }
    }
}
