package com.everlastsino.cate.entity.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.function.Predicate;

public class CrabEntity extends PathAwareEntity implements IAnimatable {
    private static final TrackedData<Integer> STATE = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final AnimationFactory factory = new AnimationFactory(this);
    private FleeEntityGoal<PlayerEntity> fleeEntityGoal;
    private static final Predicate<LivingEntity> STING_FILTER = entity -> (!(entity instanceof PlayerEntity) ||
            !((PlayerEntity) entity).isCreative()) && !(entity instanceof WaterCreatureEntity || entity instanceof CrabEntity || entity instanceof TurtleEntity);
    static final TargetPredicate STING_TARGET_PREDICATE = TargetPredicate.createNonAttackable().ignoreDistanceScalingFactor().ignoreVisibility().setPredicate(STING_FILTER);
    private int lastState = -1;

    public CrabEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        this.updateFleeing();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(STATE, 3);
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    public int getState() {
        return this.dataTracker.get(STATE);
    }

    public void setState(int state) {
        this.dataTracker.set(STATE, state);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 0.4f));
        this.goalSelector.add(1, new EscapeSunlightGoal(this, 0.4f));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.3f));
        this.goalSelector.add(2, new LookAtEntityGoal(this, LivingEntity.class, 8));
    }

    protected void updateFleeing() {
        if (this.fleeEntityGoal == null) {
            this.fleeEntityGoal = new FleeEntityGoal<>(this, PlayerEntity.class, 6.0f, 0.3f, 0.5f);
        }
        this.goalSelector.add(0, this.fleeEntityGoal);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isAlive()) {
            List<LivingEntity> list = this.world.getEntitiesByClass(
                    LivingEntity.class, this.getBoundingBox().expand(0.3),
                    entity -> STING_TARGET_PREDICATE.test(this, entity));
            for (LivingEntity entity : list) {
                if (!entity.isAlive()) continue;
                this.tryAttack(entity);
            }
        }
    }

    @Override
    public void mobTick() {
        this.setState(this.age > 6 && this.age <= this.getLastAttackTime() + 6 ? 2 :
                this.moveControl.isMoving() ? 1 : 3);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getState() == this.lastState) return PlayState.CONTINUE;
        if (this.getState() == 2) setAnimation(event, "animation.crab.attack", true);
        else if (this.getState() == 1) setAnimation(event, "animation.crab.walk", true);
        else setAnimation(event, "animation.crab.idle", true);
        this.lastState = this.getState();
        return PlayState.CONTINUE;
    }

    private static <E extends IAnimatable> void setAnimation(AnimationEvent<E> event, String animation, boolean loop) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation(animation, loop));
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return world.getBlockState(this.getBlockPos().down()).isOf(Blocks.SAND) || world.getFluidState(this.getBlockPos()).isOf(Fluids.WATER);
    }

}
