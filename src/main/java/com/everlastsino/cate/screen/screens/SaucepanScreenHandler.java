package com.everlastsino.cate.screen.screens;

import com.everlastsino.cate.screen.CateScreenHandlers;
import com.everlastsino.cate.screen.screens.slots.WaterBucketSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.SmithingScreenHandler;
import net.minecraft.screen.slot.Slot;

public class SaucepanScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public SaucepanScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(CateScreenHandlers.Saucepan_ScreenHandler, syncId);
        checkSize(inventory, 11);
        this.inventory = inventory;
        //some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);

        //This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
        //This will not render the background of the slots however, this is the Screens job
        int m, l;
        //Our inventory
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
        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hot bar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    public SaucepanScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, new SimpleInventory(11));
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