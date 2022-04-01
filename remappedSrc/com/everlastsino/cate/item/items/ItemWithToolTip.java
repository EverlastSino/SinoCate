package com.everlastsino.cate.item.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemWithToolTip extends Item {
    private final Text defaultTooltip;

    public ItemWithToolTip(Text toolTip, Settings settings) {
        super(settings);
        this.defaultTooltip = toolTip;
    }

    public ItemWithToolTip(String toolTip, boolean translate, Settings settings) {
        super(settings);
        this.defaultTooltip = translate ? new TranslatableText("item.cate.tooltip." + toolTip) : Text.of(toolTip);
    }

    public ItemWithToolTip(String toolTip, boolean translate, Settings settings, Formatting... formatting) {
        super(settings);
        this.defaultTooltip = translate ? new TranslatableText("item.cate.tooltip." + toolTip).formatted(formatting) :
                Text.of(toolTip).getWithStyle(Style.EMPTY.withFormatting(formatting)).get(0);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(this.defaultTooltip);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
