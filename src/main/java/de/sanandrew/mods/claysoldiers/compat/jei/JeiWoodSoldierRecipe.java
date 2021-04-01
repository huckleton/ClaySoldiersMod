/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.compat.jei;

import com.google.common.collect.ImmutableList;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.crafting.WoodSoldierRecipe;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

class JeiWoodSoldierRecipe
{
    final ItemStack result;
    final List<List<ItemStack>> ingredients;

    JeiWoodSoldierRecipe(ItemStack item, String team) {
        this.result = TeamRegistry.INSTANCE.getNewTeamStack(4, team);
        this.ingredients = new ArrayList<>();

        List<ItemStack> ingredList = new ArrayList<>();
        for( ITeam teamInst : TeamRegistry.INSTANCE.getTeams() ) {
            ingredList.add(TeamRegistry.INSTANCE.getNewTeamStack(1, teamInst.getId()));
        }

        this.ingredients.add(ImmutableList.of());
        this.ingredients.add(new ArrayList<>(ingredList));
        this.ingredients.add(ImmutableList.of());
        this.ingredients.add(new ArrayList<>(ingredList));
        this.ingredients.add(ImmutableList.of(item));
        this.ingredients.add(new ArrayList<>(ingredList));
        this.ingredients.add(ImmutableList.of());
        this.ingredients.add(new ArrayList<>(ingredList));
        this.ingredients.add(ImmutableList.of());
    }

    static List<JeiWoodSoldierRecipe> getRecipes() {
        List<JeiWoodSoldierRecipe> recipes = new ArrayList<>();

        for( int i = 0; i < WoodSoldierRecipe.TEAMS.length; i++ ) {
            ItemStack item = WoodSoldierRecipe.ITEMS[i];
            String team = WoodSoldierRecipe.TEAMS[i];
            recipes.add(new JeiWoodSoldierRecipe(item, team));
        }

        return recipes;
    }
}
