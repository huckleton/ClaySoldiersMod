/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP, SilverChiren and CliffracerX
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported
   *            (http://creativecommons.org/licenses/by-nc-sa/3.0/)
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.soldier;

import de.sanandrew.mods.claysoldiers.api.NBTConstants;
import de.sanandrew.mods.claysoldiers.api.attribute.CsmMobAttributes;
import de.sanandrew.mods.claysoldiers.api.entity.IDisruptable;
import de.sanandrew.mods.claysoldiers.api.entity.ITargetingEntity;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.ISoldierEffect;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.ISoldierEffectInst;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.event.SoldierDeathEvent;
import de.sanandrew.mods.claysoldiers.api.event.SoldierTargetEnemyEvent;
import de.sanandrew.mods.claysoldiers.entity.EntityHelper;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAIFollowEnemy;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAIFollowInventory;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAIFollowTarget;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAIMoveAwayFromCorners;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAISearchInventory;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAISearchTarget;
import de.sanandrew.mods.claysoldiers.entity.ai.PathHelper;
import de.sanandrew.mods.claysoldiers.item.ItemRegistry;
import de.sanandrew.mods.claysoldiers.network.PacketManager;
import de.sanandrew.mods.claysoldiers.network.datasync.DataWatcherBooleans;
import de.sanandrew.mods.claysoldiers.network.packet.PacketSyncEffects;
import de.sanandrew.mods.claysoldiers.network.packet.PacketSyncUpgrades;
import de.sanandrew.mods.claysoldiers.registry.effect.EffectRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeEntry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.CsmConfig;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EntityClaySoldier
        extends EntityCreature
        implements ISoldier<EntityClaySoldier>, IEntityAdditionalSpawnData, ITargetingEntity<EntityClaySoldier>
{
    private static final DataParameter<String> TEAM_PARAM = EntityDataManager.createKey(EntityClaySoldier.class, DataSerializers.STRING);
    private static final DataParameter<Byte> TEXTURE_TYPE_PARAM = EntityDataManager.createKey(EntityClaySoldier.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> TEXTURE_ID_PARAM = EntityDataManager.createKey(EntityClaySoldier.class, DataSerializers.BYTE);
    private final DataWatcherBooleans<EntityClaySoldier> dwBooleans;

    private final Map<EnumUpgFunctions, ConcurrentNavigableMap<Integer, Queue<ISoldierUpgradeInst>>> upgradeFuncMap;
    private final Queue<ISoldierUpgradeInst> upgradeSyncList;
    private final Map<UpgradeEntry, ISoldierUpgradeInst> upgradeMap;
    private final Queue<EntityAIBase> removedTasks;
    private final Map<ISoldierEffect, ISoldierEffectInst> effectMap;
    private final Queue<ISoldierEffectInst> effectSyncList;

    @Nonnull
    private ItemStack doll = ItemStack.EMPTY;
    public Boolean i58O55;

    public Entity followingEntity;
    public BlockPos followingBlock;

    private double prevChasingPosX;
    private double prevChasingPosY;
    private double prevChasingPosZ;
    private double chasingPosX;
    private double chasingPosY;
    private double chasingPosZ;

    private final PathHelper pathHelper;

    public EntityClaySoldier(World world) {
        super(world);

        this.stepHeight = 0.1F;
        this.ignoreFrustumCheck = true;
        this.jumpMovementFactor = 0.2F;

        this.setSize(0.2F, 0.4F);

        this.dataManager.register(TEAM_PARAM, TeamRegistry.NULL_TEAM.getId());
        this.dataManager.register(TEXTURE_TYPE_PARAM, (byte) 0x00);
        this.dataManager.register(TEXTURE_ID_PARAM, (byte) 0x00);

        this.dwBooleans = new DataWatcherBooleans<>(this);
        this.dwBooleans.registerDwValue();

        this.upgradeFuncMap = initUpgFuncMap();
        this.upgradeSyncList = new ConcurrentLinkedQueue<>();
        this.upgradeMap = new ConcurrentHashMap<>();
        this.removedTasks = new ConcurrentLinkedQueue<>();

        this.effectSyncList = new ConcurrentLinkedQueue<>();
        this.effectMap = new ConcurrentHashMap<>();

        ((PathNavigateGround) this.getNavigator()).setCanSwim(true);

        this.pathHelper = new PathHelper(this);
    }

    public EntityClaySoldier(World world, @Nonnull ITeam team, @Nonnull ItemStack doll) {
        this(world, team);

        this.doll = doll;

        this.setLeftHanded(MiscUtils.RNG.randomInt(100) < 10);
    }

    public EntityClaySoldier(World world, @Nonnull ITeam team) {
        this(world);

        this.dataManager.set(TEAM_PARAM, team.getId());

        if( MiscUtils.RNG.randomInt(1_000_000) == 0 ) {
            int[] texIds = team.getUniqueTextureIds();
            if( texIds.length > 0 ) {
                this.setUniqueTextureId((byte) texIds[MiscUtils.RNG.randomInt(texIds.length)]);
            }
        } else if( MiscUtils.RNG.randomInt(250) == 0 ) {
            int[] texIds = team.getRareTextureIds();
            if( texIds.length > 0 ) {
                this.setRareTextureId((byte) texIds[MiscUtils.RNG.randomInt(texIds.length)]);
            }
        } else {
            int[] texIds = team.getNormalTextureIds();
            this.setNormalTextureId((byte) texIds[MiscUtils.RNG.randomInt(10) < 2 ? MiscUtils.RNG.randomInt(texIds.length) : 0]);
        }
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAIMoveAwayFromCorners(this, 0.75D));
        this.tasks.addTask(1, new EntityAIFollowTarget.Fallen(this, 1.0D));
        this.tasks.addTask(2, new EntityAIFollowTarget.Upgrade(this, 1.0D));
        this.tasks.addTask(2, new EntityAIFollowTarget.Mount(this, 1.0D));
        this.tasks.addTask(2, new EntityAIFollowInventory(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFollowTarget.King(this, 1.0D));
        this.tasks.addTask(4, new EntityAIFollowEnemy.RangedSoldier(this, 1.0D));
        this.tasks.addTask(5, new EntityAIFollowEnemy.Meelee(this, 1.0D));
        this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(7, new EntityAIWander(this, 0.5D));
        this.tasks.addTask(8, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAISearchTarget.Fallen(this));
        this.targetTasks.addTask(2, new EntityAISearchTarget.Upgrade(this));
        this.targetTasks.addTask(2, new EntityAISearchInventory(this));
        this.targetTasks.addTask(2, new EntityAISearchTarget.Mount(this));
        this.targetTasks.addTask(3, new EntityAISearchTarget.King(this));
        this.targetTasks.addTask(4, new EntityAISearchTarget.Enemy(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttributeMap().registerAttribute(CsmMobAttributes.KB_RESISTANCE);
        this.getAttributeMap().registerAttribute(CsmMobAttributes.MV_FWDIRECTION);

        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(CsmConfig.Entities.Soldiers.movementSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(CsmConfig.Entities.Soldiers.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(CsmConfig.Entities.Soldiers.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(CsmConfig.Entities.Soldiers.followRange);
    }

    //region upgrades
    @Override
    public boolean hasMainHandUpgrade() {
        return this.dwBooleans.getBit(DataWatcherBooleans.Soldier.HAS_MAINHAND_UPG.bit);
    }

    @Override
    public boolean hasOffHandUpgrade() {
        return this.dwBooleans.getBit(DataWatcherBooleans.Soldier.HAS_OFFHAND_UPG.bit);
    }

    @Override
    public boolean hasBehaviorUpgrade() {
        return this.dwBooleans.getBit(DataWatcherBooleans.Soldier.HAS_BEHAVIOR_UPG.bit);
    }

    @Override
    public void destroyUpgrade(ISoldierUpgrade upgrade, EnumUpgradeType type, boolean silent) {
        UpgradeEntry entry = new UpgradeEntry(upgrade, type);
        ISoldierUpgradeInst inst = this.upgradeMap.get(entry);

        if( inst != null ) {
            this.upgradeMap.remove(entry);
            this.upgradeSyncList.remove(inst);
            this.upgradeFuncMap.forEach((key, val) -> {
                if( Arrays.asList(UpgradeRegistry.getFuncCalls(upgrade)).contains(key) ) {
                    val.get(upgrade.getPriority()).remove(inst);
                }
            });

            switch( upgrade.getType(this) ) {
                case MAIN_HAND:
                    this.setMainhandUpg(false);
                    break;
                case OFF_HAND:
                    this.setOffhandUpg(false);
                    break;
                case BEHAVIOR:
                    this.setBehaviorUpg(false);
                    break;
            }

            upgrade.onDestroyed(this, inst);
            this.callUpgradeFunc(EnumUpgFunctions.ON_OTHR_DESTROYED, othrInst -> othrInst.getUpgrade().onUpgradeDestroyed(this, othrInst, inst));

            if( !this.world.isRemote ) {
                if( upgrade.syncData() ) {
                    this.sendSyncUpgrades(false, new UpgradeEntry(upgrade, type));
                }
            }

            if( !silent ) {
                ItemStack stack = inst.getSavedStack();
                ClaySoldiersMod.proxy.spawnParticle(EnumParticle.ITEM_BREAK, this.world.provider.getDimension(), this.posX, this.posY + this.getEyeHeight(), this.posZ,
                                                    Item.getIdFromItem(stack.getItem()), stack.getItemDamage(),
                                                    stack.hasTagCompound() ? Objects.requireNonNull(stack.getTagCompound()).toString() : "");
            }
        }
    }

    @Override
    public ISoldierUpgradeInst addUpgrade(ISoldierUpgrade upgrade, @Nonnull ItemStack stack) {
        return this.addUpgrade(upgrade, upgrade.getType(this), stack);
    }

    @Override
    public ISoldierUpgradeInst addUpgrade(ISoldierUpgrade upgrade, EnumUpgradeType type, @Nonnull ItemStack stack) {
        if( upgrade == null ) {
            return null;
        }

        ISoldierUpgradeInst upgInst = new SoldierUpgrade(upgrade, type, stack.copy().splitStack(1));

        this.addUpgradeInternal(upgInst);

        upgrade.onAdded(this, stack, upgInst);

        this.callUpgradeFunc(EnumUpgFunctions.ON_UPGRADE_ADDED, upgInstCl -> upgInstCl.getUpgrade().onUpgradeAdded(this, upgInstCl, upgInst));

        if( upgrade.syncData() && !this.world.isRemote ) {
            this.sendSyncUpgrades(true, new UpgradeEntry(upgrade, type));
        }

        return upgInst;
    }

    private void addUpgradeInternal(ISoldierUpgradeInst instance) {
        ISoldierUpgrade upgrade = instance.getUpgrade();
        UpgradeEntry entry = new UpgradeEntry(upgrade, upgrade.getType(this));

        this.upgradeMap.put(entry, instance);
        if( upgrade.syncData() ) {
            this.upgradeSyncList.add(instance);
        }
        Arrays.asList(UpgradeRegistry.getFuncCalls(upgrade)).forEach(func -> {
            Queue<ISoldierUpgradeInst> upgList = this.upgradeFuncMap.get(func).computeIfAbsent(upgrade.getPriority(), k -> new ConcurrentLinkedQueue<>());
            upgList.add(instance);
        });

        switch( upgrade.getType(this)  ) {
            case MAIN_HAND: this.setMainhandUpg(true); break;
            case OFF_HAND: this.setOffhandUpg(true); break;
            case BEHAVIOR: this.setBehaviorUpg(true); break;
        }
    }

    @Override
    public boolean hasUpgrade(String id, EnumUpgradeType type) {
        return this.hasUpgrade(UpgradeRegistry.INSTANCE.getUpgrade(id), type);
    }

    @Override
    public boolean hasUpgrade(ISoldierUpgrade upgrade, EnumUpgradeType type) {
        return upgrade != null && this.upgradeMap.containsKey(new UpgradeEntry(upgrade, type));
    }

    @Override
    public boolean canPickupUpgrade(ISoldierUpgrade upgrade, ItemStack stack) {
        if( upgrade != null ) {
            EnumUpgradeType type = upgrade.getType(this);
            return upgrade.isApplicable(this, stack) && !this.upgradeMap.containsKey(new UpgradeEntry(upgrade, type))
                           && (type != EnumUpgradeType.MAIN_HAND || !this.hasMainHandUpgrade())
                           && (type != EnumUpgradeType.OFF_HAND || !this.hasOffHandUpgrade())
                           && (type != EnumUpgradeType.BEHAVIOR || !this.hasBehaviorUpgrade());
        }

        return false;
    }

    @Override
    public long countUpgradesOfType(EnumUpgradeType type) {
        return this.upgradeMap.entrySet().stream().filter(entry -> entry.getKey().type == type).count();
    }

    public void pickupUpgrade(EntityItem item) {
        ItemStack stack = item.getItem();

        if( stack.getCount() < 1 ) {
            return;
        }

        ISoldierUpgrade upg = UpgradeRegistry.INSTANCE.getUpgrade(stack);
        if( upg == null || this.hasUpgrade(upg, upg.getType(this)) ) {
            return;
        }

        ISoldierUpgradeInst upgInst = this.addUpgrade(upg, upg.getType(this), stack);
        if( upgInst != null ) {
            if( Arrays.asList(UpgradeRegistry.getFuncCalls(upg)).contains(EnumUpgFunctions.ON_PICKUP) ) {
                upg.onPickup(this, item, upgInst);
            }
        }
    }

    public void callUpgradeFunc(EnumUpgFunctions funcCall, final Consumer<ISoldierUpgradeInst> forEach) {
        this.upgradeFuncMap.get(funcCall).forEach((key, val) -> val.forEach(forEach));
    }

    private void sendSyncUpgrades(boolean add, UpgradeEntry... upgrades) {
        PacketManager.sendToAllAround(new PacketSyncUpgrades(this, add, upgrades), this.world.provider.getDimension(), this.posX, this.posY, this.posZ, 64.0D);
    }

    @Override
    public ISoldierUpgradeInst getUpgradeInstance(ISoldierUpgrade upgrade, EnumUpgradeType type) {
        return getUpgradeInstance(new UpgradeEntry(upgrade, type));
    }

    @Override
    public ISoldierUpgradeInst getUpgradeInstance(String upgradeId, EnumUpgradeType type) {
        return getUpgradeInstance(new UpgradeEntry(UpgradeRegistry.INSTANCE.getUpgrade(upgradeId), type));
    }

    public ISoldierUpgradeInst getUpgradeInstance(UpgradeEntry entry) {
        return this.upgradeMap.get(entry);
    }

    public void setMainhandUpg(boolean hasUpgrade) {
        this.dwBooleans.setBit(DataWatcherBooleans.Soldier.HAS_MAINHAND_UPG.bit, hasUpgrade);
    }

    public void setOffhandUpg(boolean hasUpgrade) {
        this.dwBooleans.setBit(DataWatcherBooleans.Soldier.HAS_OFFHAND_UPG.bit, hasUpgrade);
    }

    public void setBehaviorUpg(boolean hasUpgrade) {
        this.dwBooleans.setBit(DataWatcherBooleans.Soldier.HAS_BEHAVIOR_UPG.bit, hasUpgrade);
    }

    private static EnumMap<EnumUpgFunctions, ConcurrentNavigableMap<Integer, Queue<ISoldierUpgradeInst>>> initUpgFuncMap() {
        EnumMap<EnumUpgFunctions, ConcurrentNavigableMap<Integer, Queue<ISoldierUpgradeInst>>> enumMap = new EnumMap<>(EnumUpgFunctions.class);
        Arrays.asList(EnumUpgFunctions.VALUES).forEach(val -> enumMap.put(val, new ConcurrentSkipListMap<>()));

        return enumMap;
    }
    //endregion

    //region effects
    @Override
    public void expireEffect(ISoldierEffect effect) {
        ISoldierEffectInst inst = this.effectMap.get(effect);

        this.effectMap.remove(effect);
        this.effectSyncList.remove(inst);

        effect.onExpired(this, inst);

        if( !this.world.isRemote ) {
            if( effect.syncData() ) {
                this.sendSyncEffects(false, inst);
            }
        }
    }

    @Override
    public ISoldierEffectInst addEffect(ISoldierEffect effect, int duration) {
        if( effect == null ) {
            return null;
        }

        ISoldierEffectInst effInst = new SoldierEffect(effect, duration);

        this.addEffectInternal(effInst);

        effect.onAdded(this, effInst);

        if( effect.syncData() && !this.world.isRemote ) {
            this.sendSyncEffects(true, effInst);
        }

        return effInst;
    }

    private void addEffectInternal(ISoldierEffectInst instance) {
        ISoldierEffect effect = instance.getEffect();

        this.effectMap.put(effect, instance);
        if( effect.syncData() ) {
            this.effectSyncList.add(instance);
        }
    }

    private void sendSyncEffects(boolean add, ISoldierEffectInst... effects) {
        PacketManager.sendToAllAround(new PacketSyncEffects(this, add, effects), this.world.provider.getDimension(), this.posX, this.posY, this.posZ, 64.0D);
    }

    @Override
    public boolean hasEffect(String effectId) {
        return this.hasEffect(EffectRegistry.INSTANCE.getEffect(effectId));
    }

    @Override
    public boolean hasEffect(ISoldierEffect effect) {
        return this.effectMap.containsKey(effect);
    }

    @Override
    public int getEffectDurationLeft(ISoldierEffect effect) {
        return this.effectMap.containsKey(effect) ? this.effectMap.get(effect).getDurationLeft() : -1;
    }

    @Override
    public int getEffectDurationLeft(String effectId) {
        return this.getEffectDurationLeft(EffectRegistry.INSTANCE.getEffect(effectId));
    }

    @Override
    public ISoldierEffectInst getEffectInstance(String effectId) {
        return getEffectInstance(EffectRegistry.INSTANCE.getEffect(effectId));
    }

    @Override
    public ISoldierEffectInst getEffectInstance(ISoldierEffect entry) {
        return this.effectMap.get(entry);
    }
    //endregion

    //region ai and movement
    @Override
    public void setAIMoveSpeed(float speedIn) {
        super.setAIMoveSpeed(speedIn * (float) this.getEntityAttribute(CsmMobAttributes.MV_FWDIRECTION).getAttributeValue());
    }

    @Override
    public void setMoveForward(float amount) {
        super.setMoveForward(amount * (float) this.getEntityAttribute(CsmMobAttributes.MV_FWDIRECTION).getAttributeValue());
    }

    @Override
    public void setMoveStrafing(float amount) {
        super.setMoveStrafing(amount * (float) this.getEntityAttribute(CsmMobAttributes.MV_FWDIRECTION).getAttributeValue());
    }

    @Override
    public void removeTask(EntityAIBase task) {
        this.removedTasks.offer(task);
    }

    @Override
    public void setBreathableUnderwater(boolean breathable) {
        this.dwBooleans.setBit(DataWatcherBooleans.Soldier.BREATHE_WATER.bit, breathable);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if( !(entityIn instanceof EntityLivingBase) ) {
            return false;
        }

        EntityLivingBase trevor = ((EntityLivingBase) entityIn);

        MutableFloat attackDmg = new MutableFloat(this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());

        DamageSource dmgSrc = DamageSource.causeMobDamage(this);

        this.callUpgradeFunc(EnumUpgFunctions.ON_ATTACK, upg -> upg.getUpgrade().onAttack(this, upg, trevor, dmgSrc, attackDmg));

        boolean attackSuccess = trevor.attackEntityFrom(dmgSrc, attackDmg.floatValue());

        if( attackSuccess ) {
            if( this.i58O55 != null && this.i58O55 ) this.world.getEntitiesWithinAABB(EntityClaySoldier.class, this.getEntityBoundingBox().grow(1.0D), entity -> entity != null && entity != trevor && entity.getSoldierTeam() != this.getSoldierTeam()).forEach(entity -> entity.attackEntityFrom(dmgSrc, attackDmg.floatValue()));

            int fireAspectMod = EnchantmentHelper.getFireAspectModifier(this);

            if( fireAspectMod > 0 ) {
                trevor.setFire(fireAspectMod * 4);
            }

            this.applyEnchantments(this, trevor);

            float additDifficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            if( !ItemStackUtils.isValid(this.getHeldItemMainhand()) ) {
                if( this.isBurning() && this.rand.nextFloat() < additDifficulty * 0.3F ) {
                    trevor.setFire(2 * (int)additDifficulty);
                }
            }

            this.callUpgradeFunc(EnumUpgFunctions.ON_ATTACK_SUCCESS, upg -> upg.getUpgrade().onAttackSuccess(this, upg, trevor));
        }

        return attackSuccess;
    }

    @Override
    public void setFire(int seconds) {
        super.setFire(seconds);

        MutableInt modFire = new MutableInt(this.fire);
        this.callUpgradeFunc(EnumUpgFunctions.ON_SET_FIRE, upg -> upg.getUpgrade().onSetFire(this, upg, modFire));

        this.fire = modFire.getValue();
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

    @Override
    public void onUpdate() {
        this.removedTasks.forEach(this.tasks::removeTask);
        this.removedTasks.forEach(this.targetTasks::removeTask);
        this.removedTasks.clear();

        if( this.followingEntity != null && !this.followingEntity.isEntityAlive() ) {
            this.followingEntity = null;
        }
        if( this.followingBlock != null && (!this.world.getChunkFromBlockCoords(this.followingBlock).isLoaded() || !this.world.isBlockLoaded(this.followingBlock)
                                                    || this.world.isAirBlock(this.followingBlock)) )
        {
            this.followingBlock = null;
        }

        this.pathHelper.update();

        super.onUpdate();

        if( this.i58O55 == null ) { this.i58O55 = this.i58055(); if( this.i58O55 ) { this.setSize(0.34F, 0.8F); } }

        this.updateCape();
        this.updateArmSwingProgress();

        this.callUpgradeFunc(EnumUpgFunctions.ON_TICK, upg -> upg.getUpgrade().onTick(this, upg));

        this.effectMap.forEach((effect, inst) -> {
            if( inst.getDurationLeft() > 0 ) {
                inst.decreaseDuration(1);
            }

            effect.onTick(this, inst);

            if( !this.world.isRemote && inst.getDurationLeft() == 0 ) {
                this.expireEffect(effect);
            }
        });
    }

    @Override
    public boolean canBreatheUnderwater() {
        return this.dwBooleans.getBit(DataWatcherBooleans.Soldier.BREATHE_WATER.bit);
    }

    //TODO: fix look vectors
    @Override
    public boolean canEntityBeSeen(Entity target) {
//        Vec3d myVec = new Vec3d(this.posX, this.posY + this.getEyeHeight(), this.posZ);
//        Vec3d tgVec = new Vec3d(target.posX, target.posY + target.getEyeHeight(), target.posZ);
//
//        return !RayTraceFixed.rayTraceSight(this, this.world, myVec, tgVec);

        return super.canEntityBeSeen(target);
    }
    //endregion

    @Override
    public ITeam getSoldierTeam() {
        return TeamRegistry.INSTANCE.getTeam(this.dataManager.get(TEAM_PARAM));
    }

    @Override
    public double getReach() {
        return 0.5F + (this.hasUpgrade(Upgrades.MH_BONE, EnumUpgradeType.MAIN_HAND) ? 0.2F : 0.0F);
    }

    @Override
    public EntityClaySoldier getEntity() {
        return this;
    }

    @Override
    public int getTextureType() {
        return this.dataManager.get(TEXTURE_TYPE_PARAM);
    }

    @Override
    public int getTextureId() {
        return this.dataManager.get(TEXTURE_ID_PARAM);
    }

    @Override
    public void setNormalTextureId(byte id) {
        if( id < 0 || id >= this.getSoldierTeam().getNormalTextureIds().length ) {
            return;
        }

        this.dataManager.set(TEXTURE_TYPE_PARAM, (byte) 0x00);
        this.dataManager.set(TEXTURE_ID_PARAM, id);
    }

    @Override
    public void setRareTextureId(byte id) {
        if( id < 0 || id >= this.getSoldierTeam().getRareTextureIds().length ) {
            return;
        }

        this.dataManager.set(TEXTURE_TYPE_PARAM, (byte) 0x01);
        this.dataManager.set(TEXTURE_ID_PARAM, id);
    }

    @Override
    public void setUniqueTextureId(byte id) {
        if( id < 0 || id >= this.getSoldierTeam().getNormalTextureIds().length ) {
            return;
        }

        this.dataManager.set(TEXTURE_TYPE_PARAM, (byte) 0x02);
        this.dataManager.set(TEXTURE_ID_PARAM, id);
    }


    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setString(NBTConstants.E_SOLDIER_TEAM, this.dataManager.get(TEAM_PARAM));
        compound.setByte(NBTConstants.E_SOLDIER_TEXTYPE, this.dataManager.get(TEXTURE_TYPE_PARAM));
        compound.setByte(NBTConstants.E_SOLDIER_TEXID, this.dataManager.get(TEXTURE_ID_PARAM));

        ItemStackUtils.writeStackToTag(this.doll, compound, NBTConstants.E_SOLDIER_DOLL);

        NBTTagList upgrades = new NBTTagList();
        this.upgradeMap.forEach((upgEntry, upgInst) -> {
            NBTTagCompound upgNbt = new NBTTagCompound();
            upgNbt.setString(NBTConstants.N_UPGRADE_ID, MiscUtils.defIfNull(UpgradeRegistry.INSTANCE.getId(upgEntry.upgrade), ""));
            upgNbt.setByte(NBTConstants.N_UPGRADE_TYPE, (byte) upgEntry.type.ordinal());
            upgNbt.setTag(NBTConstants.N_UPGRADE_NBT, upgInst.getNbtData());
            ItemStackUtils.writeStackToTag(upgInst.getSavedStack(), upgNbt, NBTConstants.N_UPGRADE_ITEM);
            upgEntry.upgrade.onSave(this, upgInst, upgNbt);
            upgrades.appendTag(upgNbt);
        });
        compound.setTag(NBTConstants.E_SOLDIER_UPGRADES, upgrades);

        NBTTagList effects = new NBTTagList();
        this.effectMap.forEach((effect, effInst) -> {
            NBTTagCompound effNbt = new NBTTagCompound();
            effNbt.setString(NBTConstants.N_EFFECT_ID, MiscUtils.defIfNull(EffectRegistry.INSTANCE.getId(effect), ""));
            effNbt.setInteger(NBTConstants.N_EFFECT_DURATION, effInst.getDurationLeft());
            effNbt.setTag(NBTConstants.N_EFFECT_NBT, effInst.getNbtData());
            effect.onSave(this, effInst, effNbt);
            effects.appendTag(effNbt);
        });
        compound.setTag(NBTConstants.E_SOLDIER_EFFECTS, effects);

        this.dwBooleans.writeToNbt(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        String teamId = compound.getString(NBTConstants.E_SOLDIER_TEAM);
        this.dataManager.set(TEAM_PARAM, teamId instanceof String ? teamId : "");
        this.dataManager.set(TEXTURE_TYPE_PARAM, compound.getByte(NBTConstants.E_SOLDIER_TEXTYPE));
        this.dataManager.set(TEXTURE_ID_PARAM, compound.getByte(NBTConstants.E_SOLDIER_TEXID));

        if( compound.hasKey(NBTConstants.E_SOLDIER_DOLL, Constants.NBT.TAG_COMPOUND) ) {
            this.doll = new ItemStack(compound.getCompoundTag(NBTConstants.E_SOLDIER_DOLL));
        }

        NBTTagList upgrades = compound.getTagList(NBTConstants.E_SOLDIER_UPGRADES, Constants.NBT.TAG_COMPOUND);
        for( int i = 0, max = upgrades.tagCount(); i < max; i++ ) {
            NBTTagCompound upgNbt = upgrades.getCompoundTagAt(i);
            String idStr = upgNbt.getString(NBTConstants.N_UPGRADE_ID);
            byte type = upgNbt.getByte(NBTConstants.N_UPGRADE_TYPE);
            if( idStr instanceof String ) {
                ItemStack upgStack = new ItemStack(upgNbt.getCompoundTag(NBTConstants.N_UPGRADE_ITEM));
                ISoldierUpgrade upgrade = UpgradeRegistry.INSTANCE.getUpgrade(idStr);
                if( upgrade != null ) {
                    ISoldierUpgradeInst upgInst = new SoldierUpgrade(upgrade, EnumUpgradeType.VALUES[type], upgStack);
                    upgInst.setNbtData(upgNbt.getCompoundTag(NBTConstants.N_UPGRADE_NBT));
                    upgrade.onLoad(this, upgInst, upgNbt);
                    this.addUpgradeInternal(upgInst);
                }
            }
        }

        NBTTagList effects = compound.getTagList(NBTConstants.E_SOLDIER_EFFECTS, Constants.NBT.TAG_COMPOUND);
        for( int i = 0, max = effects.tagCount(); i < max; i++ ) {
            NBTTagCompound effectNbt = effects.getCompoundTagAt(i);
            String idStr = effectNbt.getString(NBTConstants.N_EFFECT_ID);
            if( idStr instanceof String ) {
                ISoldierEffect effect = EffectRegistry.INSTANCE.getEffect(idStr);
                if( effect != null ) {
                    ISoldierEffectInst effInst = new SoldierEffect(effect, effectNbt.getInteger(NBTConstants.N_EFFECT_DURATION));
                    effInst.setNbtData(effectNbt.getCompoundTag(NBTConstants.N_EFFECT_NBT));
                    effect.onLoad(this, effInst, effectNbt);
                    this.addEffectInternal(effInst);
                }
            }
        }

        this.dwBooleans.readFromNbt(compound);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        Entity srcEntity = source.getTrueSource();

        MutableFloat dmgMutable = new MutableFloat(damage);
        this.callUpgradeFunc(EnumUpgFunctions.ON_DAMAGED, inst -> inst.getUpgrade().onDamaged(this, inst, srcEntity, source, dmgMutable));
        damage = dmgMutable.floatValue();

        if( !(srcEntity instanceof EntityPlayer) && !Objects.equals(source, IDisruptable.DISRUPT_DAMAGE) ) {
            if( this.getRidingEntity() != null && MiscUtils.RNG.randomInt(4) == 0 ) {
                this.getRidingEntity().attackEntityFrom(source, damage);
                return false;
            }

            if( this.i58O55 != null && this.i58O55 ) { damage /= 3.0F; }

            if( source == DamageSource.FALL ) {
                damage *= 4.0F;
            }
        } else {
            damage = Float.MAX_VALUE;
        }

        if( super.attackEntityFrom(source, damage) ) {
            if( Objects.equals(srcEntity, this.getAttackTarget()) ) {
                this.getNavigator().clearPath();
            }

            if( srcEntity instanceof EntityLivingBase ) {
                SoldierTargetEnemyEvent evt = new SoldierTargetEnemyEvent(this, (EntityLivingBase) srcEntity, false);
                if( !ClaySoldiersMod.EVENT_BUS.post(evt) && evt.getResult() != Event.Result.DENY ) {
                    this.setAttackTarget((EntityLivingBase) srcEntity);
                }
            }

            final float fnlDamage = damage;
            this.callUpgradeFunc(EnumUpgFunctions.ON_DAMAGED_SUCCESS, inst -> inst.getUpgrade().onDamagedSuccess(this, inst, srcEntity, source, fnlDamage));

            return true;
        }

        return false;
    }

    @Override
    public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
        super.setAttackTarget(entitylivingbaseIn);
        this.followingEntity = null;
        this.followingBlock = null;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if( !this.world.isRemote ) {
            NonNullList<ItemStack> drops = NonNullList.create();

            if( ItemStackUtils.isValid(this.doll) ) {
                if( damageSource.isFireDamage() ) {
                    ItemStack brickDoll = new ItemStack(ItemRegistry.DOLL_BRICK_SOLDIER, 1);
                    drops.add(brickDoll);
                } else {
                    drops.add(this.doll);
                }
            }

            this.callUpgradeFunc(EnumUpgFunctions.ON_DEATH, inst -> inst.getUpgrade().onDeath(this, inst, damageSource, drops));

            SoldierDeathEvent event = new SoldierDeathEvent(this, damageSource, drops);
            ClaySoldiersMod.EVENT_BUS.post(event);

            EntityHelper.dropItems(this, drops);
        } else {
            ClaySoldiersMod.proxy.spawnParticle(EnumParticle.TEAM_BREAK, this.world.provider.getDimension(), this.posX, this.posY + this.getEyeHeight(), this.posZ,
                                                this.getSoldierTeam().getId());
        }
    }

    @Override
    public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
        strength *= 1.0D - this.getEntityAttribute(CsmMobAttributes.KB_RESISTANCE).getAttributeValue();

        super.knockBack(entityIn, strength, xRatio, zRatio);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource src) {
        return SoundEvents.BLOCK_GRAVEL_BREAK;//return ModConfig.useOldHurtSound ? "claysoldiers:mob.soldier.hurt" : "dig.gravel";
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_GRAVEL_STEP;
    }


    @Override
    protected void onDeathUpdate() {
        this.setDead();
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double bbEdgeLength = this.getEntityBoundingBox().getAverageEdgeLength();

        if( Double.isNaN(bbEdgeLength) ) {
            bbEdgeLength = 1.0D;
        }

        bbEdgeLength = bbEdgeLength * 320.0D;

        return distance < bbEdgeLength * bbEdgeLength;
    }

    @Override
    public boolean isInWater() {
        return super.isInWater() && this.isInsideOfMaterial(Material.WATER);
    }

    @Override
    public double getYOffset() {
        return 0.01F;
    }

    @Override
    public void disrupt() {
        this.attackEntityFrom(IDisruptable.DISRUPT_DAMAGE, Float.MAX_VALUE);
    }

    @Override
    public DisruptState getDisruptableState() {
        return DisruptState.SOLDIERS;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        PacketSyncUpgrades pktu = new PacketSyncUpgrades(this, true, this.upgradeSyncList.stream().map(entry -> new UpgradeEntry(entry.getUpgrade(), entry.getUpgradeType())).toArray(UpgradeEntry[]::new));
        pktu.toBytes(buffer);

        PacketSyncEffects pkte = new PacketSyncEffects(this, true, this.effectSyncList.toArray(new ISoldierEffectInst[0]));
        pkte.toBytes(buffer);
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        if( this.world.isRemote ) { // just making sure this gets called on the client...
            PacketSyncUpgrades pktu = new PacketSyncUpgrades();
            pktu.fromBytes(buffer);
            pktu.applyUpgrades(this);

            PacketSyncEffects pkte = new PacketSyncEffects();
            pkte.fromBytes(buffer);
            pkte.applyEffects(this);
        }
    }

    @Override
    public boolean isEnemyValid(EntityLivingBase entity) {
        if( entity != null && entity != this && entity.isEntityAlive() && this.canEntityBeSeen(entity) ) {
            SoldierTargetEnemyEvent evt = new SoldierTargetEnemyEvent(this, entity, true);
            if( !ClaySoldiersMod.EVENT_BUS.post(evt) ) {
                return evt.getResult() == Event.Result.ALLOW || (evt.getResult() != Event.Result.DENY && entity instanceof EntityClaySoldier
                                                                         && ((EntityClaySoldier)entity).getSoldierTeam() != this.getSoldierTeam());
            }
        }

        return false;
    }

    @Override
    public double getChasingPosX(float partTicks) {
        return this.prevChasingPosX + (this.chasingPosX - this.prevChasingPosX) * partTicks;
    }

    @Override
    public double getChasingPosY(float partTicks) {
        return this.prevChasingPosY + (this.chasingPosY - this.prevChasingPosY) * partTicks;
    }

    @Override
    public double getChasingPosZ(float partTicks) {
        return this.prevChasingPosZ + (this.chasingPosZ - this.prevChasingPosZ) * partTicks;
    }

    @Override
    public List<ISoldierUpgradeInst> getUpgradeInstanceList() {
        return this.upgradeMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    private void updateCape() {
        this.prevChasingPosX = this.chasingPosX;
        this.prevChasingPosY = this.chasingPosY;
        this.prevChasingPosZ = this.chasingPosZ;
        double dX = this.posX - this.chasingPosX;
        double dY = this.posY - this.chasingPosY;
        double dZ = this.posZ - this.chasingPosZ;

        if( dX > 10.0D ) {
            this.chasingPosX = this.posX;
            this.prevChasingPosX = this.chasingPosX;
        }

        if( dZ > 10.0D ) {
            this.chasingPosZ = this.posZ;
            this.prevChasingPosZ = this.chasingPosZ;
        }

        if( dY > 10.0D ) {
            this.chasingPosY = this.posY;
            this.prevChasingPosY = this.chasingPosY;
        }

        if( dX < -10.0D ) {
            this.chasingPosX = this.posX;
            this.prevChasingPosX = this.chasingPosX;
        }

        if( dZ < -10.0D ) {
            this.chasingPosZ = this.posZ;
            this.prevChasingPosZ = this.chasingPosZ;
        }

        if( dY < -10.0D ) {
            this.chasingPosY = this.posY;
            this.prevChasingPosY = this.chasingPosY;
        }

        this.chasingPosX += dX * 0.25D;
        this.chasingPosZ += dZ * 0.25D;
        this.chasingPosY += dY * 0.25D;
    }

    public boolean i58055() {
        try {
            return MiscUtils.defIfNull(this.getCustomNameTag(), "").contains(new String(new byte[] {0x5B, 0x49, 0x35, 0x38, 0x4F, 0x35, 0x35, 0x5D}, 0, 8, "ASCII"));
        } catch( UnsupportedEncodingException ignored ) { }

        return false;
    }

}
