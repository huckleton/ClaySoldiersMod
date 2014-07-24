/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.tileentity;

import de.sanandrew.core.manpack.util.NbtTypes;
import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.SAPUtils.RGBAValues;
import de.sanandrew.core.manpack.util.javatuples.Sextet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.item.IMountDoll;
import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.util.BugfixHelper;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.IThrowableUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.Iterator;
import java.util.List;

public class TileEntityClayNexus
    extends TileEntity implements IInventory
{
    public boolean isActive = false;

    public int spawnSoldierInterval = 40;
    public int spawnThrowableInterval = 30;
    public int soldierSpawnCount = 10;
    public int mountSpawnCount = 10;
    public int maxSoldierCount = 10;

    public long ticksActive = 0L;

    public float spinAngle = 0F;
    public float prevSpinAngle = 0F;

    protected String customName;

    private ItemStack[] nexusContents_ = new ItemStack[36];
    private ItemStack soldierSlot_;
    private ItemStack throwableSlot_;
    private ItemStack mountSlot_;
    private int spawningSoldierCounter_ = 0;
    private float health_ = 20.0F;

    private ClaymanTeam tempClayTeam_ = ClaymanTeam.getTeamFromName(ClaymanTeam.DEFAULT_TEAM);
    private AxisAlignedBB searchArea_;
    private AxisAlignedBB damageArea_;

    public TileEntityClayNexus() { }

    @Override
    public void updateEntity() {
        if( this.searchArea_ == null || this.damageArea_ == null ) {
            this.searchArea_ = AxisAlignedBB.getBoundingBox(this.xCoord - 63.0D, this.yCoord - 63.0D, this.zCoord - 63.0D,
                                                            this.xCoord + 64.0D, this.yCoord + 64.0D, this.zCoord + 64.0D
            );
            this.damageArea_ = AxisAlignedBB.getBoundingBox(this.xCoord + 0.1D, this.yCoord + 0.1D, this.zCoord + 0.1D,
                                                            this.xCoord + 0.9D, this.yCoord + 0.9D, this.zCoord + 0.9D
            );
        }

        super.updateEntity();

        if( this.isActive && this.soldierSlot_ != null ) {
            this.ticksActive++;

            if( !this.worldObj.isRemote ) {
                if( this.ticksActive % this.spawnSoldierInterval == 0 && this.spawningSoldierCounter_ <= 0 ) {
                    this.spawningSoldierCounter_ = Math.min(this.soldierSpawnCount, this.maxSoldierCount - this.countTeammates());
                }

                if( this.ticksActive % 5 == 0 && this.spawningSoldierCounter_ > 0 ) {
                    this.spawningSoldierCounter_ = this.maxSoldierCount - this.countTeammates();
                    if( this.spawningSoldierCounter_ > 0 ) {
                        ItemClayManDoll.spawnClayMan(this.worldObj, this.tempClayTeam_.getTeamName(), this.xCoord + 0.5F, this.yCoord + 0.2D,
                                                     this.zCoord + 0.5F
                        );
                    }
                }

                List<EntityClayMan> enemies = this.getEnemies();
                for( EntityClayMan dalek : enemies ) {
                    dalek.setPathToEntity(BugfixHelper.getEntityPathToXYZ(this.worldObj, dalek, this.xCoord, this.yCoord, this.zCoord, 10.0F,
                                                                          true, false, false, true)
                    );
                }

                if( this.ticksActive % 20 == 0 ) {
                    int dmgEnemies = this.countDamagingEnemies();
                    if( dmgEnemies > 0 ) {
                        float healthDamage = 0.5F;
                        if( dmgEnemies > 1 ) {
                            healthDamage = 0.125F * dmgEnemies;
                        }
                        this.health_ -= healthDamage;
                        this.markDirty();
                        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                    }
                }
            }
        }

        if( this.worldObj.isRemote ) {
            this.prevSpinAngle = this.spinAngle;
            if( this.isActive ) {
                this.spinAngle += 4;
                ClaymanTeam team = ItemClayManDoll.getTeam(this.soldierSlot_);
                RGBAValues rgba = SAPUtils.getRgbaFromColorInt(team.getTeamColor());
                CSM_Main.proxy.spawnParticles(PacketParticleFX.FX_NEXUS, Sextet.with((double)this.xCoord, (double)this.yCoord, (double)this.zCoord,
                                                                                     rgba.getRed() / 255.0F, rgba.getGreen() / 255.0F, rgba.getBlue() / 255.0F));
            } else if( this.spinAngle % 90 != 0 ) {
                this.spinAngle += 2;
            }

            if( this.spinAngle >= 360 ) {
                this.prevSpinAngle = -1;
                this.spinAngle = 0;
            }
        }
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return 39;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        switch( slot ) {
            case 0:
                return soldierSlot_;
            case 1:
                return throwableSlot_;
            case 2:
                return mountSlot_;
            default:
                return this.nexusContents_[slot - 3];
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int reduceAmount) {
        ItemStack stack = this.getStackInSlot(slot);

        if( stack != null ) {
            if( stack.stackSize <= reduceAmount ) {
                this.setStackInSlot(slot, null);
                this.markDirty();
                return stack;
            } else {
                ItemStack splitStack = stack.splitStack(reduceAmount);

                if( stack.stackSize == 0 ) {
                    this.setStackInSlot(slot, null);
                }

                this.markDirty();
                return splitStack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = this.getStackInSlot(slot);
        if( stack != null ) {
            this.setStackInSlot(slot, null);
            return stack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack newStack) {
        this.setStackInSlot(slot, newStack);

        if( newStack != null && newStack.stackSize > this.getInventoryStackLimit() ) {
            newStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : CSM_Main.MOD_ID + ":container.nexus";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.customName != null;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this
                && p_70300_1_.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() { }

    @Override
    public void closeInventory() { }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        switch( slot ) {
            case 0:
                return stack == null || stack.getItem() == ModItems.dollSoldier;
            case 1:
                ASoldierUpgrade upgrade = SoldierUpgrades.getUpgradeFromItem(stack);
                return stack == null || upgrade instanceof IThrowableUpgrade;
            case 2:
                return stack == null || stack.getItem() instanceof IMountDoll;
            default:
                return stack == null || SoldierUpgrades.getUpgradeFromItem(stack) != null;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.nexusContents_ = new ItemStack[36];

        NBTTagList nbttaglist = nbt.getTagList("items", NbtTypes.NBT_COMPOUND);
        for( int i = 0; i < nbttaglist.tagCount(); i++ ) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int slot = nbttagcompound1.getByte("slot") & 255;

            if( slot >= 0 && slot < this.getSizeInventory() ) {
                this.setStackInSlot(slot, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }

        this.isActive = nbt.getBoolean("active");
        this.health_ = nbt.getFloat("health");
        this.spawnSoldierInterval = nbt.getInteger("spawnSldInterval");
        this.spawnThrowableInterval = nbt.getInteger("spawnThrwInterval");
        this.soldierSpawnCount = nbt.getInteger("spawnSldCount");
        this.mountSpawnCount = nbt.getInteger("spawnMntCount");
        this.maxSoldierCount = nbt.getInteger("maxSldCount");

        if( nbt.hasKey("customName") ) {
            customName = nbt.getString("customName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        NBTTagList nbttaglist = new NBTTagList();
        for( int slot = 0; slot < this.getSizeInventory(); slot++ ) {
            ItemStack slotStack = this.getStackInSlot(slot);
            if( slotStack != null ) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("slot", (byte) slot);
                slotStack.writeToNBT(itemTag);
                nbttaglist.appendTag(itemTag);
            }
        }
        nbt.setTag("items", nbttaglist);

        nbt.setBoolean("active", this.isActive);
        nbt.setFloat("health", this.health_);
        nbt.setInteger("spawnSldInterval", this.spawnSoldierInterval);
        nbt.setInteger("spawnThrwInterval", this.spawnThrowableInterval);
        nbt.setInteger("spawnSldCount", this.soldierSpawnCount);
        nbt.setInteger("spawnMntCount", this.mountSpawnCount);
        nbt.setInteger("maxSldCount", this.maxSoldierCount);

        if( this.hasCustomInventoryName() ) {
            nbt.setString("customName", this.customName);
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }

    private int countTeammates() {
        @SuppressWarnings("unchecked")
        List<EntityClayMan> soldiers = this.worldObj.getEntitiesWithinAABB(EntityClayMan.class, this.searchArea_);
        int cnt = 0;
        for( EntityClayMan dodger : soldiers ) {
            if( dodger.getClayTeam().equals(this.tempClayTeam_.getTeamName()) ) {
                cnt++;
            }
        }
        return cnt;
    }

    private List<EntityClayMan> getEnemies() {
        @SuppressWarnings("unchecked")
        List<EntityClayMan> soldiers = this.worldObj.getEntitiesWithinAABB(EntityClayMan.class, this.searchArea_);
        int cnt = 0;
        Iterator<EntityClayMan> iterator = soldiers.iterator();
        while( iterator.hasNext() ) {
            if( iterator.next().getClayTeam().equals(this.tempClayTeam_.getTeamName()) ) {
                iterator.remove();
            }
        }
        return soldiers;
    }

    private int countDamagingEnemies() {
        @SuppressWarnings("unchecked")
        List<EntityClayMan> soldiers = this.worldObj.getEntitiesWithinAABB(EntityClayMan.class, this.damageArea_);
        int cnt = 0;
        for( EntityClayMan dodger : soldiers ) {
            if( dodger.getEntityToAttack() == null && !dodger.getClayTeam().equals(this.tempClayTeam_.getTeamName()) ) {
                cnt++;
            }
        }
        return cnt;
    }

    private void setStackInSlot(int slot, ItemStack stack) {
        switch( slot ) {
            case 0:
                this.soldierSlot_ = stack;
                this.tempClayTeam_ = ItemClayManDoll.getTeam(stack);
                break;
            case 1:
                this.throwableSlot_ = stack;
                break;
            case 2:
                this.mountSlot_ = stack;
                break;
            default:
                this.nexusContents_[slot - 3] = stack;
        }
    }
}
