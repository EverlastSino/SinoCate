package com.everlastsino.cate.screen.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

@Environment(EnvType.CLIENT)
public class HearthScreen extends HandledScreen<HearthScreenHandler> {

    private static final Identifier TEXTURE = new Identifier("cate", "textures/gui/container/hearth.png");

    public HearthScreen(HearthScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
        if (this.handler.getMaxTicks() > 0) {
            float extraHeight = 14f * (this.handler.getMaxTicks() - this.handler.getRemainingTicks()) / this.handler.getMaxTicks();
            drawTexture(matrices, x + backgroundWidth / 2 - 8, (int) (y + 26 + extraHeight), 176, (int) extraHeight, 16, 16);
        }
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);
        //this.textRenderer.draw(matrices, new LiteralText(String.valueOf((int)(this.handler.getSumTicks() / 20f * 10) / 10f)),
        //        (float)(this.backgroundWidth / 2), (float)this.titleY + 10,  this.handler.getSumTicks() == 0 ? 0x404040 : 0xff0000);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        if (mouseX >= x + 80 && mouseY >= y + 24 && mouseX <= x + 96 && mouseY <= y + 40) {
            int sumTicks = this.handler.getSumTicks();
            float number = (int)(sumTicks / 20f * 10) / 10f;
            Formatting color = sumTicks > 3600 ? Formatting.GREEN : sumTicks > 1200 ? Formatting.YELLOW : sumTicks > 0 ? Formatting.RED : Formatting.WHITE;
            renderTooltip(matrices, List.of(new LiteralText(String.valueOf(number)).formatted(color)), mouseX, mouseY);
        }
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

}