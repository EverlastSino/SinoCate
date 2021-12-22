package com.everlastsino.cate.screen.screens;

import com.everlastsino.cate.screen.CateScreenHandlers;
import com.everlastsino.cate.screen.screens.slots.WaterBucketSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class SaucepanScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public PropertyDelegate propertyDelegate;

    public SaucepanScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(CateScreenHandlers.Saucepan_ScreenHandler, syncId);
        checkDataCount(propertyDelegate, 4);
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
        checkSize(inventory, 11);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        int m, l;
        //我们的自定义slot
        for (m = 0; m < 3; ++m){
            this.addSlot(new Slot(inventory, m, 44 + m * 18, 24));
        }
        for (m = 0; m < 3; ++m){
            this.addSlot(new Slot(inventory, m + 3, 44 + m * 18, 42));
        }
        for (m = 0; m < 3; ++m){
            this.addSlot(new Slot(inventory, m + 6, 152, 14 + m * 18){
                @Override
                public boolean canInsert(ItemStack stack) {
                    return false;
                }
            });
        }
        this.addSlot(new WaterBucketSlot(inventory, 10, 8, 33));
        this.addSlot(new Slot(inventory, 9, 116, 33));
        //玩家背包
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //玩家hot bar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    public SaucepanScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, new SimpleInventory(11), new ArrayPropertyDelegate(4));
    }

    public boolean isCooking(){
        return this.propertyDelegate.get(0) == 1;
    }

    public boolean isBeating(){
        return this.propertyDelegate.get(2) == 1;
    }

    public int getCookingTick(){
        return this.propertyDelegate.get(1);
    }

    public int getCookingTime(){
        return this.propertyDelegate.get(2);
    }

    public int getTimerWidth(){
        return (this.isCooking() ? 20 * this.getCookingTick() / this.getCookingTime() : 0) + 12;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

}