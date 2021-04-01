/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.IUpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.behavior.UpgradeStandardBehavior;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeBrick;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeCactus;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeIronIngot;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeNetherBrick;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeString;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeCoal;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeFlint;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeGoldIngot;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeIronBlock;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradePrismarineShard;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeSugarCane;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeWool;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeArrow;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeBlazeRod;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeBone;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeBowl;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeQuartz;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeShearBlade;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeSpeckledMelon;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeStick;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeThrowable;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeBlazePowder;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeBrownMushroom;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeButton;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeClay;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeConcretePowder;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeDiamond;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeDiamondBlock;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeEgg;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeEnderPearl;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeFeather;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeFireworkRocket;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeFireworkStar;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeFood;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGhastTear;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGlowstone;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGoggles;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGoldNugget;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGunpowder;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeLeather;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeLilyPad;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeMagmaCream;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradePaper;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradePrismarineCrystal;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeRabbitFoot;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeRabbitHide;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeRedMushroom;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeRedstone;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeSkull;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeSlimeball;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeSugar;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeWheatSeeds;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;


public final class Upgrades
{
    public static final String MH_STICK = "main_stick";
    public static final String MH_ARROW = "main_arrow";
    public static final String MH_BONE = "main_bone";
    public static final String MH_BLAZEROD = "main_blazeRod";
    public static final String MH_SPECKLEDMELON = "main_speckledMelon";
    public static final String MOH_SHEARBLADE = "main_shearBlade";
    public static final String OH_GRAVEL = "off_gravel";
    public static final String OH_SNOW = "off_snow";
    public static final String OH_FIRECHARGE = "off_fireCharge";
    public static final String OH_EMERALD = "off_emerald";
    public static final String OH_QUARTZ = "off_quartz";
    public static final String OH_BOWL = "off_bowl";

    public static final String BH_WHEAT = "behavior_wheat";
    public static final String BH_NETHERWART = "behavior_netherWart";
    public static final String BH_FERMSPIDEREYE = "behavior_fermentedSpiderEye";
    public static final String BH_ROTTENFLESH = "behavior_rottenFlesh";
    public static final String BH_SPONGE = "behavior_sponge";

    public static final String EM_FLINT = "enhance_flint";
    public static final String EM_IRONBLOCK = "enhance_ironBlock";
    public static final String EM_PRISMARINESHARD = "enhance_prismarineShard";
    public static final String EM_WOOL = "enhance_wool";
    public static final String EM_SUGARCANE = "enhance_sugarCane";
    public static final String EM_COAL = "enhance_coal";
    public static final String EM_GOLDINGOT = "enhance_goldIngot";

    public static final String MC_EGG = "misc_egg";
    public static final String MC_GLOWSTONE = "misc_glowstone";
    public static final String MC_FEATHER = "misc_feather";
    public static final String MC_FOOD = "misc_food";
    public static final String MC_GLASS = "misc_glass";
    public static final String MC_LEATHER = "misc_leather";
    public static final String MC_RABBITHIDE = "misc_rabbitHide";
    public static final String MC_SUGAR = "misc_sugar";
    public static final String MC_MAGMACREAM = "misc_magmaCream";
    public static final String MC_GOLDNUGGET = "misc_goldNugget";
    public static final String MC_REDMUSHROOM = "misc_redMushroom";
    public static final String MC_GUNPOWDER = "misc_gunpowder";
    public static final String MC_FIREWORKSTAR = "misc_fireworkStar";
    public static final String MC_BROWNMUSHROOM = "misc_brownMushroom";
    public static final String MC_SKULL = "misc_skull";
    public static final String MC_PAPER = "misc_paper";
    public static final String MC_CONCRETEPOWDER = "misc_concretePowder";
    public static final String MC_BUTTON = "misc_button";
    public static final String MC_CLAY = "misc_clay";
    public static final String MC_GHASTTEAR = "misc_ghastTear";
    public static final String MC_REDSTONE = "misc_redstone";
    public static final String MC_SLIMEBALL = "misc_slimeball";
    public static final String MC_DIAMOND = "misc_diamond";
    public static final String MC_DIAMONDBLOCK = "misc_diamondBlock";
    public static final String MC_ENDERPEARL = "misc_enderPearl";
    public static final String MC_WHEATSEEDS = "misc_seeds";
    public static final String MC_BLAZEPOWDER = "misc_blazePowder";
    public static final String MC_LILYPAD = "misc_lilyPad";
    public static final String MC_RABBITFOOT = "misc_rabbitFoot";
    public static final String MC_PRISMCRYSTALS = "misc_prismarineCrystals";
    public static final String MC_FIREWORKROCKET = "misc_fireworkRocket";

    public static final String CR_IRONINGOT = "core_ironIngot";
    public static final String CR_BRICK = "core_brick";
    public static final String CR_STRING = "core_string";
    public static final String CR_CACTUS = "core_cactus";
    public static final String CR_NETHERBRICK = "core_netherBrick";

    public static void initialize(IUpgradeRegistry registry) {
        registry.registerUpgrade(MH_STICK, new UpgradeStick());
        registry.registerUpgrade(MH_ARROW, new UpgradeArrow());
        registry.registerUpgrade(MH_BONE, new UpgradeBone());
        registry.registerUpgrade(MH_BLAZEROD, new UpgradeBlazeRod());
        registry.registerUpgrade(MOH_SHEARBLADE, new UpgradeShearBlade());
        registry.registerUpgrade(OH_GRAVEL, new UpgradeThrowable.Gravel());
        registry.registerUpgrade(OH_SNOW, new UpgradeThrowable.Snow());
        registry.registerUpgrade(OH_FIRECHARGE, new UpgradeThrowable.Firecharge());
        registry.registerUpgrade(OH_EMERALD, new UpgradeThrowable.Emerald());
        registry.registerUpgrade(MH_SPECKLEDMELON, new UpgradeSpeckledMelon());
        registry.registerUpgrade(OH_QUARTZ, new UpgradeQuartz());
        registry.registerUpgrade(OH_BOWL, new UpgradeBowl());

        registry.registerUpgrade(BH_WHEAT, new UpgradeStandardBehavior("wheat", new ItemStack(Items.WHEAT)));
        registry.registerUpgrade(BH_NETHERWART, new UpgradeStandardBehavior("netherwart", new ItemStack(Items.NETHER_WART)));
        registry.registerUpgrade(BH_FERMSPIDEREYE, new UpgradeStandardBehavior("fermentedspidereye", new ItemStack(Items.FERMENTED_SPIDER_EYE)));
        registry.registerUpgrade(BH_ROTTENFLESH, new UpgradeStandardBehavior("rottenflesh", new ItemStack(Items.ROTTEN_FLESH)));
        registry.registerUpgrade(BH_SPONGE, new UpgradeStandardBehavior("sponge", new ItemStack(Blocks.SPONGE, 1, OreDictionary.WILDCARD_VALUE)));

        registry.registerUpgrade(EM_FLINT, new UpgradeFlint());
        registry.registerUpgrade(EM_IRONBLOCK, new UpgradeIronBlock());
        registry.registerUpgrade(EM_PRISMARINESHARD, new UpgradePrismarineShard());
        registry.registerUpgrade(EM_WOOL, new UpgradeWool());
        registry.registerUpgrade(EM_SUGARCANE, new UpgradeSugarCane());
        registry.registerUpgrade(EM_COAL, new UpgradeCoal());
        registry.registerUpgrade(EM_GOLDINGOT, new UpgradeGoldIngot());

        registry.registerUpgrade(MC_EGG, new UpgradeEgg());
        registry.registerUpgrade(MC_GLOWSTONE, new UpgradeGlowstone());
        registry.registerUpgrade(MC_FEATHER, new UpgradeFeather());
        registry.registerUpgrade(MC_FOOD, new UpgradeFood());
        registry.registerUpgrade(MC_GLASS, new UpgradeGoggles());
        registry.registerUpgrade(MC_LEATHER, new UpgradeLeather());
        registry.registerUpgrade(MC_RABBITHIDE, new UpgradeRabbitHide());
        registry.registerUpgrade(MC_SUGAR, new UpgradeSugar());
        registry.registerUpgrade(MC_MAGMACREAM, new UpgradeMagmaCream());
        registry.registerUpgrade(MC_GOLDNUGGET, new UpgradeGoldNugget());
        registry.registerUpgrade(MC_REDMUSHROOM, new UpgradeRedMushroom());
        registry.registerUpgrade(MC_GUNPOWDER, new UpgradeGunpowder());
        registry.registerUpgrade(MC_FIREWORKSTAR, new UpgradeFireworkStar());
        registry.registerUpgrade(MC_BROWNMUSHROOM, new UpgradeBrownMushroom());
        registry.registerUpgrade(MC_SKULL, new UpgradeSkull());
        registry.registerUpgrade(MC_PAPER, new UpgradePaper());
        registry.registerUpgrade(MC_CONCRETEPOWDER, new UpgradeConcretePowder());
        registry.registerUpgrade(MC_BUTTON, new UpgradeButton());
        registry.registerUpgrade(MC_CLAY, new UpgradeClay());
        registry.registerUpgrade(MC_GHASTTEAR, new UpgradeGhastTear());
        registry.registerUpgrade(MC_REDSTONE, new UpgradeRedstone());
        registry.registerUpgrade(MC_SLIMEBALL, new UpgradeSlimeball());
        registry.registerUpgrade(MC_DIAMOND, new UpgradeDiamond());
        registry.registerUpgrade(MC_DIAMONDBLOCK, new UpgradeDiamondBlock());
        registry.registerUpgrade(MC_ENDERPEARL, new UpgradeEnderPearl());
        registry.registerUpgrade(MC_WHEATSEEDS, new UpgradeWheatSeeds());
        registry.registerUpgrade(MC_BLAZEPOWDER, new UpgradeBlazePowder());
        registry.registerUpgrade(MC_LILYPAD, new UpgradeLilyPad());
        registry.registerUpgrade(MC_RABBITFOOT, new UpgradeRabbitFoot());
        registry.registerUpgrade(MC_PRISMCRYSTALS, new UpgradePrismarineCrystal());
        registry.registerUpgrade(MC_FIREWORKROCKET, new UpgradeFireworkRocket());

        registry.registerUpgrade(CR_IRONINGOT, new UpgradeIronIngot());
        registry.registerUpgrade(CR_BRICK, new UpgradeBrick());
        registry.registerUpgrade(CR_STRING, new UpgradeString());
        registry.registerUpgrade(CR_CACTUS, new UpgradeCactus());
        registry.registerUpgrade(CR_NETHERBRICK, new UpgradeNetherBrick());
    }
}
