package io.github.redrain0o0.bedrock.client.screens;

import io.github.redrain0o0.bedrock.Bedrockmod;
import io.github.redrain0o0.bedrock.client.gui.components.json.JsonUIScreenBackButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;

public class JsonUIScreen extends Screen {
    protected Screen lastScreen;
    public Component title;

    public JsonUIScreen(Screen screen, Component component) {
        super(component);
        this.lastScreen = screen;
        this.title = component;
    }

    @Override
    protected void init() {
        this.addRenderableWidget(JsonUIScreenBackButton.builder().screen(this.lastScreen).build());
    }

    public void renderSideBar(GuiGraphics guiGraphics, int i, int j, float f) {
        guiGraphics.blitSprite(RenderType::guiTextured, Bedrockmod.createId("tiles/side_bar"), 0, 23, 31, this.height);
    }

    public void renderWidgets(GuiGraphics guiGraphics, int i, int j, float f) {
        for (Renderable renderable : super.renderables) {
            renderable.render(guiGraphics, i, j, f);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        this.renderBackground(guiGraphics, i, j, f);
        guiGraphics.blitSprite(RenderType::guiTextured, Bedrockmod.createId("tiles/title_bar"), 0, 0, this.width, 23);
        guiGraphics.drawString(this.font, this.title, 28, 5, 5000268);
    }
}
