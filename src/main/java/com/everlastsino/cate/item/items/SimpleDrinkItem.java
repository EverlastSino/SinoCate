package com.everlastsino.cate.item.items;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class SimpleDrinkItem extends Item {
    private final ItemStack container;
    private SoundEvent drinkSound;

    public SimpleDrinkItem(Item container, Settings settings) {
        super(settings);
        this.container = new ItemStack(container);
        this.drinkSound = SoundEvents.ENTITY_GENERIC_DRINK;
    }

    public SimpleDrinkItem drinkSound(SoundEvent event) {
        this.drinkSound = event;
        return this;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        Object serverPlayerEntity;
        super.finishUsing(stack, world, user);
        if (user instanceof ServerPlayerEntity) {
            serverPlayerEntity = user;
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)serverPlayerEntity, stack);
            ((PlayerEntity)serverPlayerEntity).incrementStat(Stats.USED.getOrCreateStat(this));
        }
        if (!world.isClient) {
            user.removeStatusEffect(StatusEffects.POISON);
        }
        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        }
        if (user instanceof PlayerEntity playerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
            if (!playerEntity.getInventory().insertStack(this.container.copy())) {
                playerEntity.dropItem(this.container.copy(), false);
            }
        }
        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return this.drinkSound;
    }

    @Override
    public SoundEvent getEatSound() {
        return this.drinkSound;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}