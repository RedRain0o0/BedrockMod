package io.github.redrain0o0.bedrock.client;

import io.github.redrain0o0.bedrock.client.gui.BedrockHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

public class BedrockmodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((guiGraphics, tickDelta) -> new BedrockHud(Minecraft.getInstance()).render(guiGraphics));
    }
}
