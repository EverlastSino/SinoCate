package com.everlastsino.cate.item.items;

import com.everlastsino.cate.api.enums.RollingPinTypes;
import com.everlastsino.cate.item.CateItems;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RollingPinItem extends MiningToolItem {

    public RollingPinItem(Settings settings) {
        super(4.0f, -3.2f, ToolMaterials.WOOD, BlockTags.WOOL, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient && context.getPlayer() != null && context.getPlayer().isSneaking()
                && context.getWorld().getBlockState(context.getBlockPos()).isOf(Blocks.CRAFTING_TABLE)) {
            NbtCompound nbtCompound = context.getStack().getOrCreateNbt();
            if (!nbtCompound.contains("mold_type")) {
                nbtCompound.putInt("mold_type", RollingPinTypes.DOUGH.id);
                context.getStack().setNbt(nbtCompound);
                context.getPlayer().sendMessage(RollingPinTypes.DOUGH.text, true);
            } else {
                RollingPinTypes moldType = RollingPinTypes.valueOf(nbtCompound.getInt("mold_type")).getNext();
                nbtCompound.putInt("mold_type", moldType.id);
                context.getStack().setNbt(nbtCompound);
                context.getPlayer().sendMessage(moldType.text, true);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient || user.isSneaking()) return super.use(world, user, hand);
        NbtCompound nbtCompound = user.getStackInHand(hand).getOrCreateNbt();
        if (nbtCompound.contains("mold_type")) {
            user.sendMessage(RollingPinTypes.valueOf(nbtCompound.getInt("mold_type")).text, true);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (nbtCompound.contains("mold_type")) {
            RollingPinTypes type = RollingPinTypes.valueOf(nbtCompound.getInt("mold_type"));
            tooltip.add(type.text);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    public static RollingPinTypes getRollingPinType(ItemStack itemStack) {
        NbtCompound nbtCompound = itemStack.getOrCreateNbt();
        if (nbtCompound.contains("mold_type")) {
            return RollingPinTypes.valueOf(nbtCompound.getInt("mold_type"));
        }
        return RollingPinTypes.DOUGH;
    }

}
