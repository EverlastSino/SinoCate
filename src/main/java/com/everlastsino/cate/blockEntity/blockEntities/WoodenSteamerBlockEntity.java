package com.everlastsino.cate.blockEntity.blockEntities;

import com.everlastsino.cate.block.CateBlocks;
import com.everlastsino.cate.blockEntity.CateBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WoodenSteamerBlockEntity extends BlockEntity {
    public boolean enableUp;
    public boolean canLit;
    public boolean lit;
    public int floor;
    public DefaultedList<ItemStack> upItems;
    public int[] upTimes;
    public DefaultedList<ItemStack> downItems;
    public int[] downTimes;

    public WoodenSteamerBlockEntity(BlockPos pos, BlockState state) {
        super(CateBlockEntities.Wooden_Steamer_BlockEntity, pos, state);
        this.enableUp = this.getEnableUp();
        this.canLit = false;
        this.lit = false;
        this.floor = this.getFloors(pos);
        this.upItems = DefaultedList.ofSize(8, ItemStack.EMPTY);
        this.upTimes = new int[]{0, 0, 0, 0, 0, 0};
        this.downItems = DefaultedList.ofSize(8, ItemStack.EMPTY);
        this.downTimes = new int[]{0, 0, 0, 0, 0, 0};
    }

    public void updateBlock() {
        this.enableUp = this.getEnableUp();
        this.floor = this.getFloors(this.pos);
        this.canLit = this.getLitCond(this.pos);
        this.setLit();
        this.toInitialChunkDataNbt();
    }

    public void setLit() {
        if(this.world != null){
            this.world.setBlockState(this.pos, this.getCachedState().with(Properties.LIT, this.lit));
        }
    }

    public boolean getEnableUp(){
        return this.getCachedState().get(Properties.SLAB_TYPE) == SlabType.DOUBLE;
    }

    public boolean hasFire(BlockState blockState){
        return blockState.isOf(Blocks.CAMPFIRE) || blockState.isOf(Blocks.SOUL_CAMPFIRE);
    }

    public int getFloors(BlockPos pos){
        int floors = 1;
        BlockPos locatePos = pos.down();
        if(this.world != null){
            BlockState blockState = this.world.getBlockState(locatePos);
            while (blockState.isOf(CateBlocks.Wooden_Steamer)){
                BlockEntity entity = this.world.getBlockEntity(locatePos);
                if (entity instanceof WoodenSteamerBlockEntity entity1 && entity1.enableUp){
                    floors++;
                    locatePos = locatePos.down();
                } else {
                    return floors;
                }
            }
        }
        return floors;
    }

    public boolean getLitCond(BlockPos pos) {
        BlockPos locatePos = pos.down(this.floor);
        if(this.world != null){
            BlockState cauldronState = this.world.getBlockState(locatePos);
            return cauldronState.isOf(Blocks.WATER_CAULDRON) && cauldronState.get(Properties.LEVEL_3) > 0 &&
                    this.hasFire(this.world.getBlockState(locatePos.down()));
        }
        return false;
    }

    public boolean insertItem(BlockPos pos, ItemStack itemStack, ItemStack itemBeingCooked, int cookingTime){
        int floors = this.floor;
        while(this.world != null && floors >= 0){
            BlockEntity blockEntity = this.world.getBlockEntity(pos.down(floors - 1));
            if(blockEntity instanceof WoodenSteamerBlockEntity entity){
                this.updateBlock();
                for(int i = 0; i < 4; ++i){
                    if(entity.downItems.get(i).isEmpty()){
                        entity.downItems.set(i, itemStack);
                        entity.downItems.set(i + 4, itemBeingCooked);
                        entity.downTimes[i] = cookingTime;
                        this.updateBlock();
                        return true;
                    }
                }
                if(entity.enableUp){
                    for(int i = 0; i < 4; ++i){
                        if(entity.upItems.get(i).isEmpty()){
                            entity.upItems.set(i, itemStack);
                            entity.upItems.set(i + 4, itemBeingCooked);
                            entity.upTimes[i] = cookingTime;
                            this.updateBlock();
                            return true;
                        }
                    }
                }
            }
            floors--;
        }
        return false;
    }

    public ItemStack pickItem(BlockPos pos, boolean isPick){
        int floors = this.floor;
        while(this.world != null && floors >= 0) {
            BlockEntity blockEntity = this.world.getBlockEntity(pos.down(this.floor - floors));
            if(blockEntity instanceof WoodenSteamerBlockEntity entity){
                if(entity.enableUp){
                    for(int i = 3; i >= 0; --i){
                        if(!entity.upItems.get(i).isEmpty()){
                            ItemStack itemStack = entity.upItems.get(i);
                            if(isPick) {
                                entity.upItems.set(i, ItemStack.EMPTY);
                                entity.upItems.set(i + 4, ItemStack.EMPTY);
                                entity.upTimes[i] = 0;
                            }
                            this.updateBlock();
                            return itemStack;
                        }
                    }
                }
                for(int i = 3; i >= 0; --i){
                    if(!entity.downItems.get(i).isEmpty()){
                        ItemStack itemStack = entity.downItems.get(i);
                        if(isPick) {
                            entity.downItems.set(i, ItemStack.EMPTY);
                            entity.downItems.set(i + 4, ItemStack.EMPTY);
                            entity.downTimes[i] = 0;
                        }
                        this.updateBlock();
                        return itemStack;
                    }
                }
            }
            floors--;
        }
        return ItemStack.EMPTY;
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, T t) {
        if(world != null && t instanceof WoodenSteamerBlockEntity entity){
            if(entity.floor > 5){
                return;
            }
            entity.updateBlock();
            if(entity.canLit){
                Random random = new Random();
                if(!world.isClient){
                    boolean isLit = false;
                    for (int i = 0; i < 4 && i < entity.upTimes.length && i < entity.downTimes.length; ++i) {
                        if (entity.upTimes[i] <= 0) {
                            entity.upItems.set(i, entity.upItems.get(i + 4));
                        } else {
                            entity.upTimes[i]--;
                            isLit = true;
                        }
                        if (entity.downTimes[i] <= 0) {
                            entity.downItems.set(i, entity.downItems.get(i + 4));
                        } else {
                            entity.downTimes[i]--;
                            isLit = true;
                        }
                        entity.lit = isLit;
                    }
                    if (entity.lit && random.nextInt(2400) == 1) {
                        BlockState cauldronState = world.getBlockState(blockPos.down(entity.floor));
                        int level = cauldronState.get(Properties.LEVEL_3);
                        world.setBlockState(blockPos.down(entity.floor), cauldronState.with(Properties.LEVEL_3, Math.max(1, level - 1)));
                        if (level - 1 <= 0) {
                            world.setBlockState(blockPos.down(entity.floor), Blocks.CAULDRON.getDefaultState());
                        }
                    }
                }
                if(world.isClient && random.nextInt(100) == 1){
                    DefaultParticleType defaultParticleType = ParticleTypes.CAMPFIRE_SIGNAL_SMOKE;
                    world.addImportantParticle(defaultParticleType, true,
                            (double)entity.pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1),
                            (double)entity.pos.getY() + random.nextDouble() + random.nextDouble(),
                            (double)entity.pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1),
                            0.0D, 0.07D, 0.0D);
                }
            } else {
                entity.lit = false;
            }
        }
    }

    public DefaultedList<ItemStack> getSaveStacks() {
        DefaultedList<ItemStack> list = DefaultedList.ofSize(8, ItemStack.EMPTY);
        for (int i = 0; i < 4; ++i) {
            list.set(i, this.upItems.get(i));
            list.set(i + 4, this.downItems.get(i));
        }
        return list;
    }

    public void setSaveStacks(DefaultedList<ItemStack> list) {
        for (int i = 0; i < 4; ++i) {
            this.upItems.set(i, list.get(i));
            this.downItems.set(i, list.get(i + 4));
        }
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        DefaultedList<ItemStack> list = DefaultedList.ofSize(8, ItemStack.EMPTY);
        Inventories.readNbt(tag, list);
        this.setSaveStacks(list);
        this.upTimes = tag.getIntArray("uptime");
        this.downTimes = tag.getIntArray("downtime");
        this.enableUp = tag.getBoolean("up");
        this.canLit = tag.getBoolean("can");
        this.lit = tag.getBoolean("lit");
        this.floor = tag.getInt("floor");
        this.updateBlock();
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        Inventories.writeNbt(tag, this.getSaveStacks());
        tag.putIntArray("uptime", this.upTimes);
        tag.putIntArray("downtime", this.downTimes);
        tag.putBoolean("up", this.enableUp);
        tag.putBoolean("can", this.canLit);
        tag.putBoolean("lit", this.lit);
        tag.putInt("floor", this.floor);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    private void updateListeners() {
        if (this.world == null || this.world.isClient) return;
        this.markDirty();
        this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        this.updateListeners();
        NbtCompound nbt = new NbtCompound();
        this.writeNbt(nbt);
        return nbt;
    }

}