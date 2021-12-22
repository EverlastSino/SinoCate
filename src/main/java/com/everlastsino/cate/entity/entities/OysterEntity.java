package com.everlastsino.cate.entity.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class OysterEntity extends WaterCreatureEntity {

    public OysterEntity(EntityType<? extends OysterEntity> entityType, World world) {
        super(entityType, world);
    }

    public int getMaxAir() {
        return 2 << 10;
    }

    protected void initGoals() {
        this.goalSelector.add(0, new EscapeLandGoal(this, 0.2D));
        this.goalSelector.add(0, new EscapeDangerGoal(this, 0.2D));
        this.goalSelector.add(1, new LookAroundGoal(this));
        this.goalSelector.add(1, new WanderAroundGoal(this, 0.1D));
    }

    protected void tickWaterBreathingAir(int air) {
        if (this.isAlive() && !this.isInsideWaterOrBubbleColumn()) {
            this.setAir(air - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.damage(DamageSource.DROWN, 0.5F);
            }
        } else {
            this.setAir(MathHelper.clamp(this.getAir() + 100, 0, this.getMaxAir()));
        }

    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return world.getFluidState(this.getBlockPos()).isIn(FluidTags.WATER);
    }
}

class EscapeLandGoal extends Goal {
    protected final PathAwareEntity mob;
    protected final double speed;
    protected double targetX;
    protected double targetY;
    protected double targetZ;
    protected boolean active;

    public EscapeLandGoal(PathAwareEntity mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    public boolean canStart() {
        if (!this.mob.world.getBlockState(this.mob.getBlockPos().up()).isOf(Blocks.WATER)) {
            BlockPos blockPos = this.locateClosestWater(this.mob.world, this.mob);
            if (blockPos != null) {
                this.targetX = blockPos.getX();
                this.targetY = blockPos.getY();
                this.targetZ = blockPos.getZ();
                return true;
            }
        }
        return false;
    }

    public void start() {
        this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
        this.active = true;
    }

    public void stop() {
        this.active = false;
    }

    public boolean shouldContinue() {
        return !this.mob.getNavigation().isIdle();
    }

    @Nullable
    protected BlockPos locateClosestWater(BlockView blockView, Entity entity) {
        BlockPos blockPos = entity.getBlockPos();
        int i = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();
        float f = (float)(5 * 5 * 4 * 2);
        BlockPos blockPos2 = null;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int l = i - 5; l <= i + 5; ++l) {
            for(int m = j - 4; m <= j + 4; ++m) {
                for(int n = k - 5; n <= k + 5; ++n) {
                    mutable.set(l, m, n);
                    if (blockView.getFluidState(mutable).isIn(FluidTags.WATER)) {
                        float g = (float)((l - i) * (l - i) + (m - j) * (m - j) + (n - k) * (n - k));
                        if (g < f) {
                            f = g;
                            blockPos2 = new BlockPos(mutable);
                        }
                    }
                }
            }
        }

        return blockPos2;
    }
}
