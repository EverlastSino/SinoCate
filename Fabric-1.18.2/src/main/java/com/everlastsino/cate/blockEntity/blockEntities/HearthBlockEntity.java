package com.everlastsino.cate.blockEntity.blockEntities;

import com.everlastsino.cate.api.CateBlockEntityInventory;
import com.everlastsino.cate.api.enums.HearthSituation;
import com.everlastsino.cate.block.blocks.HearthBlock;
import com.everlastsino.cate.blockEntity.CateBlockEntities;
import com.everlastsino.cate.screen.screens.HearthScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HearthBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, CateBlockEntityInventory {
    public DefaultedList<ItemStack> fuel = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public int remainingTicks;
    public int sumTicks;
    public int maxTicks;
    public PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0 -> {
                    return HearthBlockEntity.this.remainingTicks;
                }
                case 1 -> {
                    return HearthBlockEntity.this.sumTicks;
                }
                case 2 -> {
                    return HearthBlockEntity.this.maxTicks;
                }
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> HearthBlockEntity.this.remainingTicks = value;
                case 1 -> HearthBlockEntity.this.sumTicks = value;
                case 2 -> HearthBlockEntity.this.maxTicks = value;
            }
        }

        @Override
        public int size() {
            return 3;
        }
    };

    public HearthBlockEntity(BlockPos pos, BlockState state) {
        super(CateBlockEntities.Hearth_BlockEntity, pos, state);
    }

    public ItemStack getFuel() {
        return this.fuel.get(0);
    }

    public void decreaseFuel() {
        ItemStack fuel = this.getFuel();
        if (fuel.isOf(Items.LAVA_BUCKET)) this.fuel.set(0, new ItemStack(Items.BUCKET));
        else fuel.decrement(1);
    }

    public static <E extends BlockEntity> void tick(World world, BlockPos pos, BlockState blockState, E e) {
        if (e instanceof HearthBlockEntity entity) {
            if (entity.remainingTicks > 0) --entity.remainingTicks;
            else if (entity.remainingTicks == 0 && !entity.getFuel().isEmpty()) {
                entity.remainingTicks = AbstractFurnaceBlockEntity.createFuelTimeMap()
                        .getOrDefault(entity.getFuel().getItem(), 0);
                entity.maxTicks = entity.remainingTicks;
                if (entity.remainingTicks > 0) {
                    entity.decreaseFuel();
                }
            }
            entity.updateSituation();
        }
    }

    public void updateSituation() {
        if (this.world == null || this.world.isClient) return;
        this.sumTicks = this.remainingTicks + this.getFuel().getCount() * this.maxTicks;
        this.world.setBlockState(this.pos, this.getCachedState().with(
                HearthBlock.SITUATION, HearthSituation.getSituation(this.sumTicks)));
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.fuel;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {

    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(this.getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new HearthScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.fuel);
        this.remainingTicks = nbt.getInt("tick");
        this.updateSituation();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.fuel);
        nbt.putInt("tick", this.remainingTicks);
    }
}
