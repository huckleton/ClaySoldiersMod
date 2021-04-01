/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.team;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.ITeamRegistry;
import de.sanandrew.mods.claysoldiers.util.Resources;
import net.minecraft.util.ResourceLocation;

public final class Teams
{
    public static final String SOLDIER_CLAY = "clay";
    public static final String SOLDIER_RED = "clay_red";
    public static final String SOLDIER_YELLOW = "clay_yellow";
    public static final String SOLDIER_GREEN = "clay_green";
    public static final String SOLDIER_BLUE = "clay_blue";
    public static final String SOLDIER_ORANGE = "clay_orange";
    public static final String SOLDIER_MAGENTA = "clay_magenta";
    public static final String SOLDIER_LIGHTBLUE = "clay_lightBlue";
    public static final String SOLDIER_LIME = "clay_lime";
    public static final String SOLDIER_PINK = "clay_pink";
    public static final String SOLDIER_CYAN = "clay_cyan";
    public static final String SOLDIER_PURPLE = "clay_purple";
    public static final String SOLDIER_BROWN = "clay_brown";
    public static final String SOLDIER_BLACK = "clay_black";
    public static final String SOLDIER_GRAY = "clay_gray";
    public static final String SOLDIER_WHITE = "clay_white";
    
    public static final String SOLDIER_GLASSLIGHTGRAY = "glass_lightGray";
    public static final String SOLDIER_GLASSRED = "glass_red";
    public static final String SOLDIER_GLASSYELLOW = "glass_yellow";
    public static final String SOLDIER_GLASSGREEN = "glass_green";
    public static final String SOLDIER_GLASSBLUE = "glass_blue";
    public static final String SOLDIER_GLASSORANGE = "glass_orange";
    public static final String SOLDIER_GLASSMAGENTA = "glass_magenta";
    public static final String SOLDIER_GLASSLIGHTBLUE = "glass_lightBlue";
    public static final String SOLDIER_GLASSLIME = "glass_lime";
    public static final String SOLDIER_GLASSPINK = "glass_pink";
    public static final String SOLDIER_GLASSCYAN = "glass_cyan";
    public static final String SOLDIER_GLASSPURPLE = "glass_purple";
    public static final String SOLDIER_GLASSBROWN = "glass_brown";
    public static final String SOLDIER_GLASSBLACK = "glass_black";
    public static final String SOLDIER_GLASSGRAY = "glass_gray";
    public static final String SOLDIER_GLASSWHITE = "glass_white";
    
    public static final String SOLDIER_WOODOAK = "wood_oak";
    public static final String SOLDIER_WOODBIRCH = "wood_birch";
    public static final String SOLDIER_WOODSPRUCE = "wood_spruce";
    public static final String SOLDIER_WOODJUNGLE = "wood_jungle";
    public static final String SOLDIER_WOODACACIA = "wood_acacia";
    public static final String SOLDIER_WOODDARKOAK = "wood_darkOak";
    
    public static final String SOLDIER_CARROT = "other_carrot";
    public static final String SOLDIER_POTATO = "other_potato";
    public static final String SOLDIER_BEETROOT = "other_beet";
    public static final String SOLDIER_MELON = "other_melon";
    public static final String SOLDIER_PUMPKIN = "other_pumpkin";
    public static final String SOLDIER_REDSTONE = "other_redstone";
    public static final String SOLDIER_COAL = "other_coal";

    public static void initialize(ITeamRegistry registry) {
        registry.registerTeam(SOLDIER_CLAY, "clay", Resources.ITEM_SOLDIER_CLAY.resource, 0x8E8E86,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_CLAY.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_CLAY.resource});
        registry.registerTeam(SOLDIER_RED, "red", Resources.ITEM_SOLDIER_CLAY.resource, 0xA22823,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_RED.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_RED.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_RED.resource});
        registry.registerTeam(SOLDIER_YELLOW, "yellow", Resources.ITEM_SOLDIER_CLAY.resource, 0xFCD030,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_YELLOW.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_YELLOW_1.resource, Resources.ENTITY_SOLDIER_R_YELLOW_2.resource,
                                                      Resources.ENTITY_SOLDIER_R_YELLOW_3.resource});
        registry.registerTeam(SOLDIER_GREEN, "green", Resources.ITEM_SOLDIER_CLAY.resource, 0x56701B,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GREEN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GREEN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_GREEN_1.resource, Resources.ENTITY_SOLDIER_U_GREEN_2.resource});
        registry.registerTeam(SOLDIER_BLUE, "blue", Resources.ITEM_SOLDIER_CLAY.resource, 0x373CA1,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_BLUE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_BLUE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_BLUE.resource});
        registry.registerTeam(SOLDIER_ORANGE, "orange", Resources.ITEM_SOLDIER_CLAY.resource, 0xEE7110,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_ORANGE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_ORANGE.resource});
        registry.registerTeam(SOLDIER_MAGENTA, "magenta", Resources.ITEM_SOLDIER_CLAY.resource, 0xC64EBD,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_MAGENTA.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_MAGENTA.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_MAGENTA.resource});
        registry.registerTeam(SOLDIER_LIGHTBLUE, "lightblue", Resources.ITEM_SOLDIER_CLAY.resource, 0x41B7DE,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_LIGHTBLUE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_LIGHTBLUE.resource});
        registry.registerTeam(SOLDIER_LIME, "lime", Resources.ITEM_SOLDIER_CLAY.resource, 0x77BF1A,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_LIME.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_LIME.resource});
        registry.registerTeam(SOLDIER_PINK, "pink", Resources.ITEM_SOLDIER_CLAY.resource, 0xEF95B2,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_PINK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_PINK.resource});
        registry.registerTeam(SOLDIER_CYAN, "cyan", Resources.ITEM_SOLDIER_CLAY.resource, 0x159095,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_CYAN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_CYAN.resource});
        registry.registerTeam(SOLDIER_PURPLE, "purple", Resources.ITEM_SOLDIER_CLAY.resource, 0x7D2BAD,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_PURPLE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_PURPLE_1.resource, Resources.ENTITY_SOLDIER_R_PURPLE_2.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_PURPLE_1.resource, Resources.ENTITY_SOLDIER_U_PURPLE_2.resource});
        registry.registerTeam(SOLDIER_BROWN, "brown", Resources.ITEM_SOLDIER_CLAY.resource, 0x784C2C,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_BROWN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_BROWN_1.resource, Resources.ENTITY_SOLDIER_R_BROWN_2.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_BROWN_1.resource, Resources.ENTITY_SOLDIER_U_BROWN_2.resource});
        registry.registerTeam(SOLDIER_BLACK, "black", Resources.ITEM_SOLDIER_CLAY.resource, 0x19191D,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_BLACK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_BLACK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_BLACK.resource});
        registry.registerTeam(SOLDIER_GRAY, "gray", Resources.ITEM_SOLDIER_CLAY.resource, 0x545B5E,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GRAY.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GRAY.resource});
        registry.registerTeam(SOLDIER_WHITE, "white", Resources.ITEM_SOLDIER_CLAY.resource, 0xEAEDED,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_WHITE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_WHITE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_WHITE_1.resource, Resources.ENTITY_SOLDIER_U_WHITE_2.resource});

        
        registry.registerTeam(SOLDIER_WOODOAK, "oakwood", Resources.ITEM_SOLDIER_WOOD.resource, 0xBC9862,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_WOODOAK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_WOODOAK.resource});
        registry.registerTeam(SOLDIER_WOODBIRCH, "birchwood", Resources.ITEM_SOLDIER_WOOD.resource, 0xD7CB8D,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_WOODBIRCH.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_WOODBIRCH.resource});
        registry.registerTeam(SOLDIER_WOODSPRUCE, "sprucewood", Resources.ITEM_SOLDIER_WOOD.resource, 0x805E36,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_WOODSPRUCE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_WOODSPRUCE.resource});
        registry.registerTeam(SOLDIER_WOODJUNGLE, "junglewood", Resources.ITEM_SOLDIER_WOOD.resource, 0xB88764,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_WOODJUNGLE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_WOODJUNGLE.resource});
        registry.registerTeam(SOLDIER_WOODACACIA, "acaciawood", Resources.ITEM_SOLDIER_WOOD.resource, 0xBA6337,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_WOODACACIA.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_WOODACACIA.resource});
        registry.registerTeam(SOLDIER_WOODDARKOAK, "darkoakwood", Resources.ITEM_SOLDIER_WOOD.resource, 0x492F17,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_WOODDARKOAK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_WOODDARKOAK.resource});
        
        
        registry.registerTeam(SOLDIER_GLASSLIGHTGRAY, "lightgrayglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x8E8E86,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSLIGHTGRAY.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSLIGHTGRAY.resource});
        registry.registerTeam(SOLDIER_GLASSRED, "redglass", Resources.ITEM_SOLDIER_GLASS.resource, 0xA22823,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSRED.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSRED.resource});
        registry.registerTeam(SOLDIER_GLASSYELLOW, "yellowglass", Resources.ITEM_SOLDIER_GLASS.resource, 0xFCD030,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSYELLOW.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSYELLOW.resource});
        registry.registerTeam(SOLDIER_GLASSGREEN, "greenglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x56701B,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSGREEN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSGREEN.resource});
        registry.registerTeam(SOLDIER_GLASSBLUE, "blueglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x373CA1,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSBLUE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSBLUE.resource});
        registry.registerTeam(SOLDIER_GLASSORANGE, "orangeglass", Resources.ITEM_SOLDIER_GLASS.resource, 0xEE7110,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSORANGE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSORANGE_1.resource, Resources.ENTITY_SOLDIER_R_GLASSORANGE_2.resource});
        registry.registerTeam(SOLDIER_GLASSMAGENTA, "magentaglass", Resources.ITEM_SOLDIER_GLASS.resource, 0xC64EBD,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSMAGENTA.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSMAGENTA.resource});
        registry.registerTeam(SOLDIER_GLASSLIGHTBLUE, "lightblueglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x41B7DE,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSLIGHTBLUE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSLIGHTBLUE.resource});
        registry.registerTeam(SOLDIER_GLASSLIME, "limeglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x77BF1A,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSLIME.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSLIME.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_GLASSLIME.resource});
        registry.registerTeam(SOLDIER_GLASSPINK, "pinkglass", Resources.ITEM_SOLDIER_GLASS.resource, 0xEF95B2,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSPINK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSPINK.resource});
        registry.registerTeam(SOLDIER_GLASSCYAN, "cyanglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x159095,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSCYAN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSCYAN.resource});
        registry.registerTeam(SOLDIER_GLASSPURPLE, "purpleglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x7D2BAD,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSPURPLE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSPURPLE.resource});
        registry.registerTeam(SOLDIER_GLASSBROWN, "brownglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x784C2C,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSBROWN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSBROWN.resource});
        registry.registerTeam(SOLDIER_GLASSBLACK, "blackglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x19191D,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSBLACK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSBLACK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_BLACK.resource});
        registry.registerTeam(SOLDIER_GLASSGRAY, "grayglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x545B5E,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSGRAY.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSGRAY.resource});
        registry.registerTeam(SOLDIER_GLASSWHITE, "whiteglass", Resources.ITEM_SOLDIER_GLASS.resource, 0xEAEDED,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSWHITE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSWHITE.resource});

        
        registry.registerTeam(SOLDIER_CARROT, "carrot", Resources.ITEM_SOLDIER_CARROT.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_CARROT.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_CARROT.resource});
        registry.registerTeam(SOLDIER_POTATO, "potato", Resources.ITEM_SOLDIER_POTATO.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_POTATO.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_POTATO.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_POTATO.resource});
        registry.registerTeam(SOLDIER_BEETROOT, "beetroot", Resources.ITEM_SOLDIER_BEETROOT.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_BEETROOT.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_BEETROOT.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_BEETROOT.resource});
        registry.registerTeam(SOLDIER_MELON, "melon", Resources.ITEM_SOLDIER_MELON.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_MELON.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_MELON.resource});
        registry.registerTeam(SOLDIER_PUMPKIN, "pumpkin", Resources.ITEM_SOLDIER_PUMPKIN.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_PUMPKIN_1.resource, Resources.ENTITY_SOLDIER_N_PUMPKIN_2.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_PUMPKIN_1.resource, Resources.ENTITY_SOLDIER_R_PUMPKIN_2.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_PUMPKIN_1.resource, Resources.ENTITY_SOLDIER_U_PUMPKIN_2.resource});
        registry.registerTeam(SOLDIER_REDSTONE, "redstone", Resources.ITEM_SOLDIER_REDSTONE.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_REDSTONE_1.resource, Resources.ENTITY_SOLDIER_N_REDSTONE_2.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_REDSTONE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_REDSTONE_1.resource, Resources.ENTITY_SOLDIER_U_REDSTONE_2.resource});
        registry.registerTeam(SOLDIER_COAL, "coal", Resources.ITEM_SOLDIER_COAL.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_COAL.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_COAL.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_COAL.resource});
    }
}
