package de.sanandrew.mods.claysoldiers.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.core.manpack.util.client.ItemRenderHelper;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.client.util.Textures;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class RenderSoldierLeftHandEvent
{
    private final ItemStack itemShearBlade_ = new ItemStack(RegistryItems.shearBlade);
    private final ItemStack blockGravel_ = new ItemStack(Blocks.gravel);
    private final ItemStack blockSnow_ = new ItemStack(Blocks.snow);
    private final ItemStack blockObsidian_ = new ItemStack(Blocks.obsidian); //TODO: substitude until proper texture arrives
    private final ItemStack blockEmerald_ = new ItemStack(Blocks.emerald_block);

    @SubscribeEvent
    public void onSoldierRender(SoldierRenderEvent event) {
        if( event.stage == SoldierRenderEvent.RenderStage.EQUIPPED ) {
            if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_SHEARLEFT)) ) {
                this.renderLeftHandItem(event.clayMan, event.clayManRender, this.itemShearBlade_);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_GRAVEL)) ) {
                this.renderThrowableBlock(event.clayMan, event.clayManRender, this.blockGravel_);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_SNOW)) ) {
                this.renderThrowableBlock(event.clayMan, event.clayManRender, this.blockSnow_);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_FIRECHARGE)) ) {
                this.renderThrowableBlock(event.clayMan, event.clayManRender, this.blockObsidian_);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_EMERALD)) ) {
                this.renderThrowableBlock(event.clayMan, event.clayManRender, this.blockEmerald_);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_BOWL)) ) {
                this.renderShield(event.clayMan, event.clayManRender);
            }
        }
    }

    private void renderShield(EntityClayMan clayMan, RenderClayMan renderer) {
        IIcon icon = clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_IRON_BLOCK)) ? Textures.shieldStudIcon : Textures.shieldIcon;

        GL11.glPushMatrix();
        renderer.modelBipedMain.bipedLeftArm.postRender(0.0625F);
        GL11.glTranslatef(-0.4F, 0.15F, -0.2F);
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        ItemRenderHelper.renderIconIn3D(icon, false, false, 0xFFFFFF);
        GL11.glPopMatrix();
    }

    private void renderThrowableBlock(EntityClayMan clayMan, RenderClayMan renderer, ItemStack stack) {
        GL11.glPushMatrix();
        renderer.modelBipedMain.bipedLeftArm.postRender(0.0625F);
        GL11.glTranslatef(0.05F, 0.55F, 0.0F);
        GL11.glScalef(0.3F, 0.3F, 0.3F);
        renderer.getItemRenderer().renderItem(clayMan, stack, 0);
        GL11.glPopMatrix();
    }

    private void renderLeftHandItem(EntityClayMan clayMan, RenderClayMan renderer, ItemStack stack) {
        GL11.glPushMatrix();
        renderer.modelBipedMain.bipedLeftArm.postRender(0.0625F);
        GL11.glTranslatef(-0.1F, 0.6F, 0F);

        float itemScale = 0.6F;
        GL11.glScalef(itemScale, itemScale, itemScale);
        GL11.glRotatef(140F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);

        renderer.getItemRenderer().renderItem(clayMan, stack, 0);
        GL11.glPopMatrix();
    }
}
