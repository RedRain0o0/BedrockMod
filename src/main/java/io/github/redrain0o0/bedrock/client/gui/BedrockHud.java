package io.github.redrain0o0.bedrock.client.gui;

import io.github.redrain0o0.bedrock.Bedrockmod;
import io.github.redrain0o0.bedrock.client.BedrockmodClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class BedrockHud {
    private final Minecraft minecraft;
    private final Player player;
    public BedrockHud(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.player = minecraft.player;
    }
    public void render(GuiGraphics guiGraphics) {
        assert player != null;
        Component position = Component.translatable("bedrock.hud.coords", player.getBlockX(), player.getBlockY(), player.getBlockZ());
        guiGraphics.fill(0, 50, this.minecraft.font.width(position) + 6, 62, 0, 0xBB000000);
        guiGraphics.drawString(this.minecraft.font, position, 3, 53, 16777215);
    }
}
