package com.everlastsino.cate.item.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FoodWithContainerItem extends Item {
    public ItemStack container;

    public FoodWithContainerItem(Item container, Settings settings) {
        super(settings);
        this.container = new ItemStack(container);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            if (!player.getInventory().insertStack(this.container.copy())) {
                player.dropItem(this.container.copy(), false);
            }
        }
        return itemStack;
    }

}
