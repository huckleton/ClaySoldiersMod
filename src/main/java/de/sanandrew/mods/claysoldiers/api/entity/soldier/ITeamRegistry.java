/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.entity.soldier;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public interface ITeamRegistry
{
    /**
     * registers a team with a standard implementation, which is returned if the registration succeeded.
     * @param id an unique ID for the team
     * @param name an unique name for the team
     * @param itemModel the model used for the item
     * @param itemColor the color used for the item
     * @param normalTextures the standard textures for the soldier entities, there must be at least one texture defined
     * @param rareTextures the rare textures for the soldier entities, can be null if no rare textures should be used
     * @param uniqueTextures the unique textures for the soldier entities, can be null if no unique textures should be used
     * @return a standard {@link ITeam} implementation if registration succeeded, null otherwise
     */
    ITeam registerTeam(String id, String name, ResourceLocation itemModel, int itemColor, ResourceLocation[] normalTextures, ResourceLocation[] rareTextures, ResourceLocation[] uniqueTextures);

    /**
     * registers a team with a custom implementation.
     * @param team the custom implementation of {@link ITeam}
     * @return true, if registration succeeded, false otherwise
     */
    boolean registerTeam(ITeam team);

    /**
     * gets the team instance associated with the given ID
     * @param id the ID of the wanted team
     * @return an instance of the associated team, null if no team can be found
     */
    @Nonnull
    ITeam getTeam(String id);

    /**
     * gets a list of all registered teams, sorted by the order of registration
     * @return a list of all teams
     */
    @Nonnull
    List<ITeam> getTeams();

    /**
     * gets the team instance associated with the given item
     * @param stack the ItemStack with a soldier item and saved team
     * @return an instance of the associated team, null if no team can be found
     */
    @Nonnull
    ITeam getTeam(ItemStack stack);

    /**
     * applies the team to the ItemStack
     * @param stack the ItemStack the team should be saved in
     * @param team the team the ItemStack should save
     * @return the ItemStack with the team
     */
    ItemStack setTeam(ItemStack stack, ITeam team);

    /**
     * gets a new ItemStack instance with the soldier doll item for the given team.
     * @param count the stack size
     * @param team the team
     * @return A new ItemStack holding the soldier doll item with the given team
     */
    ItemStack getNewTeamStack(int count, ITeam team);

    /**
     * gets a new ItemStack instance with the soldier doll item for the given team.
     * @param count the stack size
     * @param team the team ID
     * @return A new ItemStack holding the soldier doll item with the given team
     */
    ItemStack getNewTeamStack(int count, String team);

    /**
     * applies the team to the ItemStack
     * @param stack the ItemStack the team should be saved in
     * @param team the team ID the ItemStack should save
     * @return the ItemStack with the team
     */
    default ItemStack setTeam(ItemStack stack, String team) { return this.setTeam(stack, this.getTeam(team)); }

    /**
     * registers a team with a standard implementation without unique textures, which is returned if the registration succeeded.
     * @param id an unique ID for the team
     * @param name an unique name for the team
     * @param itemModel the model used for the item
     * @param itemColor the color used for the item
     * @param normalTextures the standard textures for the soldier entities, there must be at least one texture defined
     * @param rareTextures the rare textures for the soldier entities, can be null if no rare textures should be used
     * @return a standard {@link ITeam} implementation if registration succeeded, null otherwise
     */
    default ITeam registerTeam(String id, String name, ResourceLocation itemModel, int itemColor, ResourceLocation[] normalTextures, ResourceLocation[] rareTextures) {
        return this.registerTeam(id, name, itemModel, itemColor, normalTextures, rareTextures, null);
    }

    /**
     * registers a team with a standard implementation without rare and unique textures, which is returned if the registration succeeded.
     * @param id an unique ID for the team
     * @param name an unique name for the team
     * @param itemModel the model used for the item
     * @param itemColor the color used for the item
     * @param normalTextures the standard textures for the soldier entities, there must be at least one texture defined
     * @return a standard {@link ITeam} implementation if registration succeeded, null otherwise
     */
    default ITeam registerTeam(String id, String name, ResourceLocation itemModel, int itemColor, ResourceLocation[] normalTextures) {
        return this.registerTeam(id, name, itemModel, itemColor, normalTextures, null, null);
    }
}
