package com.everlastsino.cate.screen.screens;

import com.everlastsino.cate.screen.CateScreenHandlers;
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

public class StoneMillScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public PropertyDelegate propertyDelegate;

    public StoneMillScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(CateScreenHandlers.Stone_Mill_ScreenHandler, syncId);
        checkDataCount(propertyDelegate, 2);
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
        checkSize(inventory, 7);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        int m, l;
        //我们的自定义slot
        for (m = 0; m < 3; ++m){
            this.addSlot(new Slot(inventory, m, 30 + m * 18, 40));
        }
        for (m = 0; m < 3; ++m){
            this.addSlot(new Slot(inventory, m + 3, 138, 21 + m * 18){
                @Override
                public boolean canInsert(ItemStack stack) {
                    return false;
                }
            });
        }
        this.addSlot(new Slot(inventory, 6, 102, 40));
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

    public StoneMillScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, new SimpleInventory(7), new ArrayPropertyDelegate(2));
    }

    public int getTimerWidth() {
        return this.getRequireRounds() > 0 ? 3 * this.getStep() / this.getRequireRounds() : 0;
    }

    public int getRequireRounds() {
        return this.propertyDelegate.get(0);
    }

    public int getStep() {
        return this.propertyDelegate.get(1);
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