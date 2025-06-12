package io.github.redrain0o0.bedrock.client.screens;

import io.github.redrain0o0.bedrock.Bedrockmod;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class JsonUISettingsScreen extends JsonUIScreen{
    protected final Options options;
    public JsonUISettingsScreen(Screen screen, Options options, Component component) {
        super(screen, component);
        this.lastScreen = screen;
        this.title = Component.translatable("menu.options").withStyle(Style.EMPTY.withShadowColor(0));
        this.options = options;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        guiGraphics.blitSprite(RenderType::guiTextured, Bedrockmod.createId("tiles/split_json"), (int)(this.width * 0.4F), 22, 4, this.height - 22);
        super.renderWidgets(guiGraphics, i, j, f);
    }
}
